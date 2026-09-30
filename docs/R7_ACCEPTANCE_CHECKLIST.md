# R7 native acceptance checklist

**Run against a copied world and only a freshly compiled R7 JAR. All checks here remain outstanding until executed locally.** Record client/server versions and active integration/claim-mod versions. Keep old historical logs separate from `validation/r7`.

## Build and automatic suites

1. `BUILD.bat`: native compile, JUnit and JAR assembly pass on Java 21. Confirm mod metadata is 0.0.1a.R7 and no test fixtures/optional mods are bundled.
2. `VALIDATE_R7.bat`: positive report of at least 102 required server tests passed. R7 contributes 20 tests across transfer gates, greenhouse import/output, protection denial/owner attribution, post-listener mutation, sync, chest viewers, geometry, recipe invalidation, unlock generations and persistence.
3. `VALIDATE_R7.bat integrations`: retain fresh logs showing Mekanism, GT, MI, AE2, GrandPower and KubeJS are actually loaded and their tests passed. Presence-gated tests on an absent mod are not integration passes. No datapack/script errors may be ignored.
4. After those gates, `python tools/check_release.py` compares the built classes/resources with source and checks the fresh evidence. It is expected to fail before a real JAR exists and does not certify client/profiling/claim tests.

## Automation matrix

Check Transfer Upgrades and an external pipe against every face and Auto/Input/Output/Disabled mode. Toggle itemAutomation with pipes already attached. Simulated insertion/extraction must not mutate storage. Battery and upgrade slots obey their separate existing options. Test a full neighbor and absent capability. Leave blocked crops pending, free output space, and confirm exactly one recovery without loss/duplication. Test greenhouse front import and export independently; internal tending must not be mistaken for external automation.

## Protection matrix

Use native event cancellation tests, then your actual claim mod. Place the controller inside the owner's claim, across the claim boundary, and in a denied area. Verify BUILD/DEMOLISH/FARMLAND/WATER/PLANT/HARVEST/GROW/LEAF_HARVEST. Denial must leave Calculator's resources, FE, crop age, world target, counters and drops unchanged. Demolition must never queue recovery before permitted removal. Test permissions changing while the same controller stays loaded; there is no positive permission cache.

Verify the owner UUID and per-target attribution. Unowned controllers pause by default, assign ownership only through authorized use, and do not replace an existing owner. Ownerless compatibility mode uses a synthetic actor and still fires events. Remote/unloaded chunks and bedrock/block entities must remain untouched. Inspect blocked-action diagnostics.

The predicted place event exposes proposed state with the old world/snapshot, so verify claims which inspect snapshot/current world separately. Permission-aware plant adapters must guard every touched position. Leave opaque callbacks disabled first; test sapling/external plant limitations. A deliberate legacy opt-in needs a separate boundary test and must not be recorded as universal claim safety. Future fluid flow and arbitrary third-party side effects are outside the guard.

## Gameplay, restart and client

Process dirty chips, portable calculations, research unlocks, modules, smelting, dynamic calculators, crop tending and native energy input/output. Verify recipe selection after counted/component input edits, research grant/revoke, config changes and datapack/script reload. Runtime-configuration editing code must call settingsChanged after raw ConfigValue.set.

Open a reinforced chest with two players, close one, close the other, disconnect a viewer, die, change dimensions and unload/reload the chunk. Lid closes correctly and does not remain saved open. Include an opener-only test with no globally scanned players.

Change sides while a machine is idle/disabled/redstone-paused/output-blocked and confirm visible updates. Test multiple GUI scales, the Power report, held item sizes from R5, and guide recipe links/default-value note. Read-only diagnostics must not transmit power or load a remote chunk.

Save/restart with partially processed recipes, full pending outputs, surplus energy/items, nondefault sides and greenhouse fallow data. Verify owner and research remain, and geometry/structure validity refresh after restart and rotation. Preserve configured excess stored energy rather than clamping it away.

## Performance evidence

Profile comparable R6/R7 scenes: idle and active processing banks, loaded reinforced chests with zero/one/many viewers, all greenhouse tiers and mixed power networks. Record hardware, pack, counts, MSPT percentiles, allocation/GC behavior and block-entity network packet counts. Toggle each performance option to isolate effects. Test chunk unload/reload and world shutdown for retained objects. Do not infer a percentage speedup or leak-free status from source inspection or the isolated checks.

No local pass of this checklist is implied by its presence in the ZIP.
