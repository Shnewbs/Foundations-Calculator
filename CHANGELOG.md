# 0.0.5a.R2 — native bank transfers and continued 26.3 migration

- Removed the standard FE integer ceiling from direct transfers between Calculator storage banks. Live long-valued sender/receiver limits and exact fill-fraction balancing now apply.
- Preserved FE policy and face direction gates; external standard FE receivers retain bounded integer transfers.
- Added runtime regressions for a single 15-billion-FE balanced transfer, disabled FE ports and output-only receiver faces (134-test floor).
- Cumulative: includes all 0.0.5a.R1 and earlier .5/.4/.3 updates.
- Automatic CurseForge publication uses the exact audited GitHub release binary, project 1734096 and the CURSEFORGE_API_TOKEN repository secret. Candidate names select alpha/beta/release.

Playable platform: Minecraft 1.21.1 / NeoForge 21.1.250 / Java 21. The separate 26.3 branch is migrating to shared-journal machine energy capabilities on Java 25; it remains unfinished and no 26.3 binary is included. 26.4 preparation is platform isolation, not verified compatibility. Manual client, copied-world, multiplayer and performance acceptance remain pending.
