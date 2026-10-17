# 0.1.0alpha.R4 — performance, names and native rendering fixes

- Fixes untranslated block-item names in 26.x with the proper block description prefix; adds English item-key aliases for existing stacks and external item-name queries.
- Fixes the 26.x world renderer using overlay zero, which samples the hurt/red overlay; normal models now use Minecraft's NO_OVERLAY value.
- Broadens NeoForge requirements within each Minecraft target: 1.21.1 starts at 21.1.219, 26.1.2 at 26.1.2.100, and 26.3 at 26.3.0.39-beta. Minimum-version native compile/runtime validation gates publication, separately from current-pin validation.
- Reuses exact upgraded energy costs when the actual cost, upgrade count and discount are unchanged. Changes remain live; decimal rounding and long-valued conservation are preserved.
- Removes repeated overflow-bank scans and capacity-key allocations in chamber output processing/export.
- Machine menus avoid calculating energy/capacity for unrelated telemetry fields; bulk menus share one display inventory instead of allocating one per bin.
- Reuses immutable pipe connection shapes, greenhouse growth tables, direction arrays, machine IDs and renderer texture identifiers; avoids repeated lamp-position construction and unchanged energy-display dirty writes.
- Adds native client checks for every registered item name, untinted model submission and overflow paging on both 26.x ports, plus full connection-shape and exact arithmetic regressions.
- Default recipes, probabilities, timings, energy costs, scan intervals, protection gates and progression remain unchanged. This revision remains alpha.

# 0.1.0alpha.R3 — configurable chamber outputs

- Extraction Chamber and Precision Chamber each support 6–54 output slots through their own `machine.<id>.outputSlots` server setting. Default capacity stays six.
- Expanded outputs scroll by mouse wheel or the scrollbar. Each viewer selects their own row; shift-clicking and automation use the same saved inventory.
- Existing six output slots, batteries and upgrades retain their saved indices. Extra outputs use a separate save key. Lowering capacity leaves previously saved overflow accessible until collected.
- Process recipes accept up to 54 independently rolled results. The 1.21.1 KubeJS schema and CraftTweaker builder accept larger tables; native ports support datapacks. JEI/EMI layouts accommodate the full table.
- Existing 910 authored recipes, probabilities, timings, energy costs and progression are unchanged. Custom sieve/ore recipes are opt-in pack additions.
- Adds native save, capacity, automation, rollback, menu and codec checks plus a real 1.21.1 client walkthrough for both chamber scroll controls.
- Requires matching client/server versions. This is an alpha revision; broader beta acceptance remains pending.

# 0.1.0beta — long operation scaling and release validation candidate

- Native processing, reinforced smelting, analysis costs, research, dynamic calculations, greenhouse actions and weather/multiplier operations use the canonical long FE balance and full scaled cost.
- Portable calculation commits now spend exact long-valued costs; installed Energy Upgrades apply exact decimal discount math without an intermediate integer cap.
- Native item charging/discharging can move more than 2,147,483,647 FE per operation while preserving FE policies. Standard external FE interfaces retain their bounded contract.
- Machine menus synchronize the full 64-bit required cost. Jade, recipe viewers, guide recipes and read-only diagnostics use long cost/rate values. Gameplay protocol increases from 6 to 7, requiring matching client/server versions.
- Adds eight JUnit arithmetic regressions and eight native gameplay regressions for high costs, conservation, upgrades, menus, charging policies and saved inventories/upgrades/owner/energy state. Native runtime floor increases from 134 to 142.
- Adds one local/CI validation command with source, binary and sources-JAR fingerprints and fresh build/runtime evidence. Candidate classification understands `0.1.0beta` as beta; unfinished port names remain ineligible for automatic publication.
- Cumulative: retains the 0.0.5a.R2 progression, configuration, native bank balancing and integrated Guide API.

This is a development candidate until the beta acceptance gates pass. The supported native build target is Minecraft 1.21.1 / NeoForge 21.1.250 / Java 21. The 26.1.2 and 26.3 ports remain separate migration work. No 26.4 compatibility or historical 1.12 world conversion is claimed.
