> Published release candidate: **0.1.0-rc.1** for Minecraft 1.21.1, 26.1.2 and 26.3. [Validation and upload receipts](docs/RC1_VALIDATION.md) · [Audit and acceptance limits](docs/RC1_AUDIT.md). Historical status below is retained for provenance.

# 0.1.0beta development candidate

Latest published update: **alpha R2** on 1.21.1, 26.1.2 and 26.3.
See [R2 validation, performance comparison and accepted upload receipts](docs/releases/0.1.0alpha.R2.md).

The source target is 0.1.0beta. The last published GitHub/CurseForge binary remains 0.0.5a.R2; full beta acceptance is pending.

Minecraft 1.21.1 builds native binary/source JARs and passes 31 JUnit tests, 142 standalone GameTests and 142 combined-integration GameTests including GTCEu. The hosted real OpenGL client walkthrough reaches all completion markers and captures 21 base-client screenshots. Screenshot review caught a clipped Field Guide chapter strip and a stale portable-output capture. Both fixes are verified by the final base-client run; the viewport fix is also pushed and compiled on both port branches.

Minecraft 26.1.2 and 26.3 now both build native binary/source JARs, pass 36 platform JUnit tests, and pass 46 real-world assertions in standalone and matching-integration server runs. Coverage includes actual item/energy capabilities, shared balances, aborted transactions, live sided gates, inventory/bulk/owner/pending-output ValueIO persistence, exact long balances and all four saplings. Both servers load 909 Calculator recipes; the 910th authored recipe requires an optional glowstone-ingot tag. The 26.3 tree resources use the native feature registry and all four features load.

Native port runtime evidence: Actions 38333014353 (26.1.2) and 38333018796 (26.3). Final base-client evidence: Actions 38336044777; final native release audit: Actions 38336044783. These checks complement the remaining client/integration, real-world upgrade, survival/multiplayer/protection and performance acceptance; they do not mark those cumulative gates complete.

Use matching client/server builds: gameplay protocol 7 on 1.21.1 and 8 on the 26.x ports. The Guide API remains 1.0.0. The 26.x integrations pinned and exercised are JEI, Jade, AE2 and GuideMe; unavailable legacy scripting/technology integrations remain excluded on those targets.

GitHub Actions are working. The CurseForge workflow uses the repository's CURSEFORGE_API_TOKEN secret for eligible audited releases and retains upload receipts. Successful upload-workflow runs that skip an unreleased candidate are not uploads.

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
