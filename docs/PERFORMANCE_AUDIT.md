# Performance audit — 2026-10-13

## Scope and baseline

Reviewed the five remote branches in `Shnewbs/Foundations-Calculator`.
The maintained targets are `main` (1.21.1), `port/26.1.2`, and `port/26.3`.
`release/notes` and `ci/port-26.3-diagnostics` contain historical release/diagnostic
work; neither is a fourth runtime target. No remote branches were created.

Baseline heads: main `0a566754`, 26.1.2 `62f0b446`, 26.3 `5d9aca67`.
The review covered server tick entry points, recipe matching/allocation, machine
automation, energy adapters, world scans, saving, networking, client rendering,
guide indexing/caching, optional integrations and build/release workflows.
This is a source audit plus an allocator microbenchmark. Large-world profiling
and live multiplayer performance acceptance remain separate work.

## Implemented on all three targets

| Path | Change | Expected benefit / preserved behavior |
| --- | --- | --- |
| IngredientAllocation | Reject insufficient total supply before allocating a graph; direct single-ingredient allocation; reuse BFS buffers within a call | Less temporary memory. Preserves exact slot consumption, count limits and overlapping-tag max-flow correctness. |
| RecipeIndex / menus / diagnostics / research | First-match lookup traverses the sorted cached family directly | Avoids constructing a fully filtered family and redundant menu sorting. Research eligibility and deterministic recipe order stay live. |
| DynamicProgram / MachineRecipeSelection | One lazy, bounded cache per calculator lane, including input-slot offset in the cache key | Idle and underpowered lanes reuse successful and unsuccessful matches. Count, component, owner, config, research and datapack changes invalidate results; commit uses live inputs. |
| ResearchData / ResearchEvents | Batch normal mastery changes at 20-tick intervals; compare immutable per-session snapshots; skip unchanged packets | Removes the per-completion, all-player broadcast burst. Login still forces an initial packet, unlock/commands and mastery milestones sync immediately, and the 100-tick reconciliation remains. Server-wide sharing follows the existing config. |
| MachinePrograms.export | Skip adjacent inventory capability lookups when normal output slots are empty | Upgraded idle processors avoid six neighbor probes; bulk backing stores retain their original path. |
| MachineBlockEntity.pushEnergy | Stop probing faces when the canonical buffer is empty | Empty stores and a buffer drained on an earlier face avoid subsequent receiver probes; live side/policy gates remain. |
| MachineBlockEntity.serverTick | Query neighbor redstone only for signal-dependent modes; reuse immutable machine-kind sets | Default always-on machines avoid a redstone scan and repeated set allocation. |
| Magnetic flux | Empty whitelist returns before the item-entity query | Preserves the empty whitelist's existing reject-all behavior while avoiding a world scan. Blacklists retain existing behavior. |
| EnergyDisplay | Reuse the immutable unit suffix table | Avoids a small array allocation on every displayed energy value. |

## Evidence and limits

`tools/benchmark_allocator.py` compiles the current production allocator and the
pinned pre-audit Git allocator in an isolated JVM. It compares 100,000 randomized
cases for identical allocations before measuring five alternating one-million-
call samples after warm-up. Raw evidence: `performance/allocator-benchmark.json`.

On the local JDK 21.0.9, the mixed allocator workload allocated **395.2 bytes/call
before and 89.6 after: 77.3% less**. Median elapsed time was about 445.8 ns/call
before and 125.6 after, but timing varied substantially on this shared host.
Neither number is a world TPS, FPS, server tick percentile, or whole-mod memory
claim. Inputs/count arrays created by recipe callers are outside this benchmark.

Added an independent exhaustive-assignment oracle covering 3,000 randomized
allocation cases and numerical boundaries. Added real-world regressions for
first-match equivalence, lane offset/cache invalidation and immutable research
snapshots: two additional 1.21.1 GameTests and ten additional native port checks.
The native test floors are now 144 GameTests and 56 port assertions respectively.
Fresh CI evidence is recorded in `performance/native-validation.json`:

| Target | Native verification | Evidence |
| --- | --- | --- |
| 1.21.1 | 33 unit tests; 144 standalone and 144 full-integration GameTests; distributable binary/source JARs | [Native audit](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/38408010160) |
| 1.21.1 client | Actual client exited successfully, all four smoke markers passed, and all 21 required screenshots were present | [Real-client walkthrough](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/38408010593) |
| 26.1.2 | 43 unit tests; 56 real-world assertions standalone and with matching integrations; 909 required recipes; binary/source JARs | [Native audit](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/38407536817) |
| 26.3 | 43 unit tests; 56 real-world assertions standalone and with matching integrations; 909 required recipes and four tree features; binary/source JARs | [Native audit](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/38407540953) |

Unit test XML and native startup reports were inspected in the uploaded artifacts.
There were no skipped/failed/error unit tests or recipe resource errors. Local
isolated policy checks, Java syntax checks and six Python tests also passed.
The shared allocator, benchmark and display suffix changes are byte-identical
across targets; native inventory/energy APIs retain their own implementations.

The CurseForge check [ran successfully](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/38408036276)
but **did not upload**: the beta has no published, accepted GitHub release yet.
The native audit still reports `release_eligible: false`; this work does not
replace live-world upgrade, multiplayer/protection, performance soak or remaining
port client acceptance.

## Existing protections retained

Machine definitions are already immutable and indexed. Recipe family caches use
weak platform keys and clear on reload/config changes/server stop. Greenhouse
geometry caches immutable coordinates, while checking live blocks and permission
events. Machine packets already use change-only snapshots and staggered polling.
Guide catalogs/search and snapshot requests already have revision, size and rate
limits. Transaction rollback, save compatibility, exact long energy and live
capability gates remain in place.

## Remaining opportunities requiring profiles or additional correctness work

| Priority | Candidate | Required evidence / constraint |
| --- | --- | --- |
| High | Large-base world workload | Profile empty, working, output-blocked and underpowered machines at 100/1,000 units, with and without integrations. Record p50/p95/p99 tick time, allocation/GC and packet bytes on identical saves and hardware. |
| High | Magnetic flux with many dropped items | Measure entity-query/filter cost and adjacent export lookups. Spatial/query cadence changes affect motion and collection latency; test those explicitly before changing them. |
| High | Nutrition graph, locator, weather-station and mast scans | Measure their radius/visited-budget cost and synchronized tick peaks. Cache only topology with reliable block/chunk invalidation; never cache permissions, leaf maturity, loaded state or energy availability. |
| Medium | Research chamber, extractors and smelting lookup | Extend bounded selection caches if profiles show significant matching cost. Research must continue skipping known groups; extractor nutrient capacity and smelting datapack changes need independent invalidation. |
| Medium | Remaining multi-ingredient graph construction | Profile larger tag-heavy recipes. An item/ingredient candidate index must handle component-sensitive circuits, overlapping tags, scripts and reloads without changing selection order. |
| Medium | Neighbor capability caching | Use the native platform's invalidation lifecycle, accounting for chunk unload, replaced neighbors and addon policy changes. An indefinitely cached receiver is unsafe. |
| Medium | Machine screen/render text and texture identifiers | Measure visible-machine FPS/allocation before caching formatted text or texture identifiers. Invalidate on value/config/resource changes; do not reduce visual correctness or render bounds blindly. |
| Medium | 26.x transaction snapshots / ValueIO | Profile successful versus aborted automated transfers and save frequency. Full rollback snapshots currently protect inventory, bulk counts, program state and pending outputs; reduce snapshot scope only with regression coverage. |
| Low | Build and packaging overhead | Runtime artifacts already exclude test source sets; Gradle caching is configured. Measure actual CI costs before introducing cache keys or parallel builds that could mix platform artifacts. |

For supported external integrations, profile their actual runtime before changing
adapter routing. Unsupported 26.x integrations are not included in performance
or compatibility claims. The beta's world-upgrade, multiplayer/protection,
full-world performance and remaining client acceptance gates are not closed by
this audit. CurseForge publication must continue to reflect that release status.
