# Foundations Calculator 0.0.2a.R2 — final 2a candidate

Minecraft 1.21.1 · NeoForge 21.1.250 · Java 21.

`0.0.2a.R2` keeps the feature-frozen 2a systems from R1 and closes the final in-game acceptance issues found during testing: Reinforced Furnace and Analysing Chamber now actually honor Speed/Energy upgrades, machine diagnostics report the same effective upgraded FE cost, and Reinforced Chest bin icons no longer show a misleading vanilla stack count on top of the real bulk quantity.

Build with `BUILD.bat`. Final validation is `VALIDATE_0_0_2a_R2.bat`; it runs clean native build/JUnit, at least 124 standalone GameTests, integration/KubeJS GameTests and the release audit, then points to `docs/0.0.2a.R2_ACCEPTANCE.md` for copied-world acceptance.

The Foundations Guide API remains 1.0.0 and the gameplay network protocol remains 6.

Documentation: [R2 finalization](docs/0.0.2a.R2_FINALIZATION.md), [R2 acceptance](docs/0.0.2a.R2_ACCEPTANCE.md), [2a systems](docs/0.0.2a_LONG_ENERGY_AND_SCALE.md), [Guide API](guide-api/README.md).
