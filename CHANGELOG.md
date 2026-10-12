# 0.1.0beta — long operation scaling and release validation candidate

- Native processing, reinforced smelting, analysis costs, research, dynamic calculations, greenhouse actions and weather/multiplier operations use the canonical long FE balance and full scaled cost.
- Portable calculation commits now spend exact long-valued costs; installed Energy Upgrades apply exact decimal discount math without an intermediate integer cap.
- Native item charging/discharging can move more than 2,147,483,647 FE per operation while preserving FE policies. Standard external FE interfaces retain their bounded contract.
- Machine menus synchronize the full 64-bit required cost. Jade, recipe viewers, guide recipes and read-only diagnostics use long cost/rate values. Gameplay protocol increases from 6 to 7, requiring matching client/server versions.
- Adds eight JUnit arithmetic regressions and eight native gameplay regressions for high costs, conservation, upgrades, menus, charging policies and saved inventories/upgrades/owner/energy state. Native runtime floor increases from 134 to 142.
- Adds one local/CI validation command with source, binary and sources-JAR fingerprints and fresh build/runtime evidence. Candidate classification understands `0.1.0beta` as beta; unfinished port names remain ineligible for automatic publication.
- Cumulative: retains the 0.0.5a.R2 progression, configuration, native bank balancing and integrated Guide API.

This is a development candidate until the beta acceptance gates pass. The supported native build target is Minecraft 1.21.1 / NeoForge 21.1.250 / Java 21. The 26.1.2 and 26.3 ports remain separate migration work. No 26.4 compatibility or historical 1.12 world conversion is claimed.
