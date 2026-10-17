# Performance and bug audit — alpha R4

The pass covers processing and recipe lookup, long energy math and routing, output/automation inventories, greenhouse/world scans, protection events, research/network synchronization, menus, block geometry and client rendering across all three maintained branches. The attached 0.0.2a.R2 archive is historical; changes target current Git source.

| Area | Finding and action | Preserved behavior |
|---|---|---|
| Machine energy costs | One bounded per-machine cache avoids repeating exact BigDecimal discount arithmetic for identical live numeric inputs | Same flooring, saturation, upgrade limits and immediate settings/stack changes |
| Chamber output export | R3 loop bounds repeatedly called visible(), scanning the extra bank inside each slot iteration; use the fixed physical bank bound | Same slot order, side checks, item limits and off-screen recovery; no cached foreign inventories |
| Chamber pending results | Build the capacity key once and read capacity once per flush | Same pending backpressure and default six-slot storage |
| Menu telemetry | Route unrelated field IDs before calculating FE values; direct energy/capacity reads per full 42-field poll fall from 78 to 12, excluding the existing diagnostic snapshot | Same wire layout and full 64-bit values |
| Bulk menus | One display inventory per menu instead of one per bin; a 27-bin menu allocates 27 backing positions instead of 729 | Same authoritative bulk counts, icon normalization and click handling |
| Machine identity | Resolve the block registry name only when block identity changes | Facing and ACTIVE changes retain their original logic |
| Greenhouse work | Reuse the growth interval table and each lamp-scan coordinate | Same positions, visit order, tick cadence, random draws and owner/protection checks |
| Pipe geometry | Share the 64 immutable connection shapes | Exact shape equivalence checked for every mask |
| Energy displays | Only persist changed energy/capacity or the first zero snapshot | Same polling and client publication cadence |
| Renderer assets | Reuse the finite texture identifiers owned by each renderer | Resource reload still recreates renderer instances and models |
| Recipes/research/network | Existing deterministic family indexes, per-machine selectors, bounded guide payloads and changed-only research packets retained | Reload/research invalidation, commit revalidation and packet bounds remain live |
| World/foreign APIs | Loaded-chunk checks, fresh protection permission checks, external plant callbacks, and energy/item capability gates retained | No extra chunk loading, reduced scan frequency, delayed policy updates or broadened world writes |

## Measured result

`tools/benchmark_discount_cache.py` compiles the production arithmetic and cache without Minecraft stubs, checks 20,000 varied cases, alternates five JVM measurement samples and reports thread CPU/allocation counters. A local Java 21 run measured median 75.06 ns and 323.2 allocated bytes per direct discounted-cost calculation versus 2.37 ns and 0.3232 bytes per cached call in a workload with 99.9% repeated numeric inputs. This is an isolated arithmetic benchmark, not a whole-mod TPS/FPS claim. CI retains fresh results with each validated build.

## Reported bugs and compatibility

- Raw `item.foundations_calculator.research_chamber` names: 26.x block items now use the block description prefix. Every declared block and standalone item has an English name; block item-key aliases cover saved/external queries. Native client tests resolve the actual hover names, including Research Chamber.
- Red chamber models: the 26.x world submission path incorrectly passed overlay 0. It now passes `OverlayTexture.NO_OVERLAY`; client tests inspect submitted overlays and capture real world images. R5 also removes the forced white outline (zero means no outline) while forwarding genuine item outline requests.
- NeoForge range: verified minimums are 21.1.219 for Minecraft 1.21.1, 26.1.2.100 for 26.1.2, and 26.3.0.39-beta for 26.3. Current development pins remain 21.1.250, 26.1.2.114 and 26.3.0.52-beta. Each minimum must compile and run native tests before publication. Minecraft target ranges remain exact; 26.4 compatibility is not inferred.

## Validation and remaining profiling

Publication requires current-pin native tests, minimum-version native tests, translation coverage, source/binary audits, and client walkthroughs. The main suite keeps the prior processing, save, upgrade, protection, KubeJS and overflow regressions; new checks cover geometry, idle display dirtying and live arithmetic inputs. The 26.x clients verify names, render overlays and actual paged output packets.

Further gains in large farms, item magnets, foreign energy networks and huge KubeJS packs should be driven by profiles of a representative player world. This pass does not reduce gameplay work or claim completion of copied-world, multiplayer and long-duration soak acceptance.

## Native rendering follow-ups

R5 corrected the chest's forced white outline but incorrectly reused the outline value as the model-part vertex tint, making chamber frames black. R6 separates those arguments using each native API's explicit signature: white vertex tint (-1), no normal outline (0), and NO_OVERLAY. Genuine special-item outline colors are forwarded independently. Real-client assertions now check all three values and explicit colored-outline forwarding; screenshots remain a required review artifact.
