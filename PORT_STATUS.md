> Current candidate: **0.1.0-rc.1** for Minecraft 1.21.1, 26.1.2 and 26.3. [Current audit and acceptance limits](docs/RC1_AUDIT.md). Historical status below is retained for provenance.

# 26.1.2-0.1.0beta-port.1 — beta source synchronization checkpoint

Latest published update: **alpha R2** on 1.21.1, 26.1.2 and 26.3.
See [R2 validation, performance comparison and accepted upload receipts](docs/releases/0.1.0alpha.R2.md).

The cumulative 1.21.1 0.1.0beta candidate's long native operation costs, item rates, upgraded diagnostics and protocol 7 are synchronized into this platform branch while preserving its existing 26.x API migration. The arithmetic regression suite is shared. The native GameTest source floor is 142; platform fixture migration and native verification remain outstanding.

This is source parity, not a playable release. The full platform still requires compiler/API fixes, matching optional integrations, startup, saved-world and gameplay acceptance. See docs/0.1.0beta_ACCEPTANCE.md and the platform migration status. No binary or CurseForge upload is claimed.

## Performance audit — 2026-10-13

Nine source-level improvements are applied across the three maintained targets.
Fresh native suites and the 1.21.1 real-client walkthrough pass; allocator
allocation falls 77.3% in the isolated mixed workload. Full-world performance
and beta publication acceptance remain pending. See [the performance audit](docs/PERFORMANCE_AUDIT.md)
for exact CI evidence, scope and the prioritized remaining opportunities.

## Published alpha R1 — 2026-10-13

All three targets are now GitHub prereleases and CurseForge alpha uploads.
Native checks pass on each target; full beta/stable acceptance is still pending.
See [alpha R1 release receipts and performance changes](docs/releases/0.1.0alpha.R1.md).
