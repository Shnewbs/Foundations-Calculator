# 0.1.0-rc.1

Release candidate (GitHub prerelease / CurseForge beta), not stable. See docs/RC1_AUDIT.md.

- Preflight player terrain, soil/stone, screen, sickle, leaf and obsidian-key world writes through protection events; recheck callback changes before committing resources.
- Respect disabled nutrition/carrier items and open portable calculations; stop disabled manual cranking.
- Invalidate storage menus when selected module changes.
- Avoid idle smelting data writes, unchanged nutrition component writes and filtered analysis/nutrition recipe lists.
- Consolidate duplicate leaf yields and remove unreachable duplicate energy routing.
- Preserve existing recipes, defaults, chances, timing, output paging and all R6 rendering fixes.
- Add actual runtime cancellation/resource conservation/config/menu/idle-module regressions on all supported targets. Full player-world/multiplayer/long soak acceptance remains open.

# 26.1.2-0.1.0alpha.R6 — separate native vertex tint and outline requests

- Corrects the R5 model-part rendering regression: the neutral vertex tint is -1 (opaque white), while outline color zero disables outlines. R5 had treated the model-part tint argument as an outline argument, producing black chamber frames.
- Uses each Minecraft version's explicit model-part submission signature. Keeps normal models untinted with NO_OVERLAY and no outline, and forwards genuine item outlines independently.
- Client regressions now assert overlay, neutral vertex tint, normal outline and explicit colored-outline forwarding for chambers and chests.
- Retains the performance pass, translated names and verified wider NeoForge ranges. All three versions remain alpha.

# 26.1.2-0.1.0alpha.R5 — normal native model outlines

- Fixes forced white model outlines on the 26.x renderer. Normal world models use outline color zero; actual item outline requests are forwarded from Minecraft. This fixes the solid-white reinforced chest found during R4 screenshot review.
- Extends client regression checks to both chamber geometry and chest model submissions, validating NO_OVERLAY and the absence of a forced outline.
- Includes R4's performance pass, translated block-item names and expanded NeoForge compatibility ranges. All three Minecraft binaries stay on the same revision.
- The 26.x platform unit suites now also run the cached arithmetic's varied-input and validation regressions.

# 26.1.2-0.1.0alpha.R4 — performance, names and native rendering fixes

- Fixes untranslated block-item names in 26.x with the proper block description prefix; adds English item-key aliases for existing stacks and external item-name queries.
- Fixes the 26.x world renderer using overlay zero, which samples the hurt/red overlay; normal models now use Minecraft's NO_OVERLAY value.
- Broadens NeoForge requirements within each Minecraft target: 1.21.1 starts at 21.1.219, 26.1.2 at 26.1.2.100, and 26.3 at 26.3.0.39-beta. Minimum-version native compile/runtime validation gates publication, separately from current-pin validation.
- Reuses exact upgraded energy costs when the actual cost, upgrade count and discount are unchanged. Changes remain live; decimal rounding and long-valued conservation are preserved.
- Removes repeated overflow-bank scans and capacity-key allocations in chamber output processing/export.
- Machine menus avoid calculating energy/capacity for unrelated telemetry fields; bulk menus share one display inventory instead of allocating one per bin.
- Reuses immutable pipe connection shapes, greenhouse growth tables, direction arrays, machine IDs and renderer texture identifiers; avoids repeated lamp-position construction and unchanged energy-display dirty writes.
- Adds native client checks for every registered item name, untinted model submission and overflow paging on both 26.x ports, plus full connection-shape and exact arithmetic regressions.
- Default recipes, probabilities, timings, energy costs, scan intervals, protection gates and progression remain unchanged. This revision remains alpha.

# 26.1.2-0.1.0alpha.R3 — configurable chamber outputs

- Extraction Chamber and Precision Chamber each support 6–54 output slots through their own `machine.<id>.outputSlots` server setting. Default capacity stays six.
- Expanded outputs scroll by mouse wheel or the scrollbar. Each viewer selects their own row; shift-clicking and automation use the same saved inventory.
- Existing six output slots, batteries and upgrades retain their saved indices. Extra outputs use a separate save key. Lowering capacity leaves previously saved overflow accessible until collected.
- Process recipes accept up to 54 independently rolled results. The 1.21.1 KubeJS schema and CraftTweaker builder accept larger tables; native ports support datapacks. JEI/EMI layouts accommodate the full table.
- Existing 910 authored recipes, probabilities, timings, energy costs and progression are unchanged. Custom sieve/ore recipes are opt-in pack additions.
- Adds native save, capacity, automation, rollback, menu and codec checks plus a real 1.21.1 client walkthrough for both chamber scroll controls.
- Requires matching client/server versions. This is an alpha revision; broader beta acceptance remains pending.

# 0.0.5a.R2 — native bank transfers and continued 26.3 migration

- Removed the standard FE integer ceiling from direct transfers between Calculator storage banks. Live long-valued sender/receiver limits and exact fill-fraction balancing now apply.
- Preserved FE policy and face direction gates; external standard FE receivers retain bounded integer transfers.
- Added runtime regressions for a single 15-billion-FE balanced transfer, disabled FE ports and output-only receiver faces (134-test floor).
- Cumulative: includes all 0.0.5a.R1 and earlier .5/.4/.3 updates.
- Automatic CurseForge publication uses the exact audited GitHub release binary, project 1734096 and the CURSEFORGE_API_TOKEN repository secret. Candidate names select alpha/beta/release.

Playable platform: Minecraft 1.21.1 / NeoForge 21.1.250 / Java 21. The separate 26.3 branch is migrating to shared-journal machine energy capabilities on Java 25; it remains unfinished and no 26.3 binary is included. 26.4 preparation is platform isolation, not verified compatibility. Manual client, copied-world, multiplayer and performance acceptance remain pending.
