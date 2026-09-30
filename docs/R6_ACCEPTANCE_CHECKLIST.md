# R6 local acceptance

1. `BUILD.bat`: native `clean test build` must pass. Use only its newly created R6 JAR.
2. `VALIDATE_R6.bat`: standalone GameTests must positively report all required tests passed, not merely exit zero.
3. `VALIDATE_R6.bat integrations`: run with the pinned optional integrations and KubeJS. Preserve fresh logs under `validation/r6/`.
4. Client and copied-world smoke: open the Power button at multiple GUI scales; check chat output and `/foundations power` in both standalone and an integration pack. Confirm unsupported-mod profiles say not installed rather than crashing.
5. For each installed native API: charge a machine and a powered item; apply a custom ratio, each scope/direction switch and a nonzero loss; compare the native debit and FE credit. Repeat after restarting the copied world. For AE2, test the charged staff, check `itemCharging=false` on a cached view, then try unequal input/output loss settings.
6. Confirm disabled native adapters do not silently fall back under native-first routing, while deliberate FE-first routing is reported honestly. Test GT over-voltage and exhausted per-tick amp budgets.
7. Confirm R5 held-model size fixes, dirty-chip processing, research and ordinary recipes remain intact.

Large-machine-network profiling, an exhaustive survival playthrough and multiplayer acceptance are still outstanding. This update adds neither translations nor old-world migration. Do not label R6 production-ready until the native and runtime gates are completed.
