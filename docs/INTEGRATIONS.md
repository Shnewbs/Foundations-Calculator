# R7 integration status

All inherited power APIs remain compile-only and use the pinned R5 dependency versions. R7 runtime integration has **not** been certified. Read [POWER.md](POWER.md) for current R6/R7 routing rather than historical FE-first descriptions.

## Protection-aware plant extensions

Owner-aware machine callers use the overloads of FoundationsPlants accepting MachineBlockEntity. Extend PlantAdapter using default methods supportsProtectedActions(), plantProtected(..., machine), harvestProtected(..., machine), canGrowProtected(...) and growProtected(..., machine). Advertise true only when **every** affected position is protected; use MachineWorldActions loaded/permits/replace and honor cancellations without costs/drops. Raw legacy overloads remain caller-protected APIs. Opaque callbacks default off; protection.allowLegacyPlantCallbacks explicitly opts back into origin-only checks.

MachineWorldActionEvent is a cancellable server preflight on NeoForge.EVENT_BUS. Ordinary break/place events are also posted, with proposed placement state but old world/snapshot to avoid temporary denied writes. Neither the API nor isolated fixture tests certifies an actual claim mod. See [the exact semantics and limitations](R7_AUTOMATION_PROTECTION_PERFORMANCE.md).

## Retained power targets and earlier integration details

# R6 integration update — source candidate

R6 retains the dependency pins recorded below, but their R5 runtime passes are not new R6 passes. R6 adds Calculator-side J/MI overrides, scoped direction/loss policies and explicit native-first routing. **The R6 routing and policy specification in [POWER.md](POWER.md) supersedes the older FE-first/inherited-only statements below.** AE2 still has an item adapter, not a native grid port. Full R6 native/integration validation remains pending.

## Retained R5 integration reference

# Integrations and public APIs

A normal R5 installation needs only Minecraft 1.21.1, NeoForge 21.1.250 and Java 21. Optional APIs are compile-only and no dependency JARs are embedded. Install optional mods in the appropriate client/server instance yourself.

| Integration | Tested version | Behavior |
|---|---|---|
| KubeJS / Rhino | 2101.7.2-build.379 / 2101.2.7-build.81 | Native process builder, custom recipes, removal/replacement and reload |
| JEI | 19.51.0.418 | Native process categories and ingredient transfer |
| EMI | 1.1.24+1.21.1+neoforge | Native process categories, workstations and ingredient transfer |
| Jade | 15.10.6+neoforge | Server-authoritative machine HUD |
| CraftTweaker | 21.0.38 | Typed process helpers and complete JSON recipe support |
| Mekanism | 10.7.19.85 | Native energy API bridge, using Mekanism’s configured FE/joule ratio |
| GregTech CEu Modern | 8.0.0-SNAPSHOT+70db06c (20260928.082845-98) | Native EU block packets and electric items |
| Modern Industrialization | 2.5.8 | Native EU block and item ports, using MI’s FE/EU ratio |
| GrandPower | 3.0.0 (included by MI) | Long-valued FE block and item ports |
| Applied Energistics 2 | 19.2.18 | AE item energy adapter; default 2 FE/AE, configurable |
| GuideME | 21.1.19 | Required by the tested AE2 build |

Use JEI or EMI as the visible recipe browser. If both are installed, EMI controls the visible UI through its JEI bridge; the native Foundations EMI integration supplies its recipes. Jade needs the mod on the server to receive detailed machine data. FE, status and process decisions always remain on the server.

Each adapter has a `compat.*` server toggle. Restart/rejoin after changing integration switches so client registrations are refreshed. Recipe transfer checks the currently open menu, distance/held item validity, unlocked research, available ingredients, inventory space and enabled recipes; clients send only the recipe ID and requested quantity. A fill action does not create outputs or bypass power.

## CraftTweaker

Place a `.zs` file in the instance’s `scripts` directory:

```zenscript
import mods.foundations.Calculator;

Calculator.add("pack_iron_separator", "stone_separator",
    [<item:minecraft:raw_iron> * 1],
    [<item:foundations_calculator:reinforced_iron_ingot> * 2],
    500, 100);
// Calculator.remove("foundations_calculator:stone_separator/0647");
// Calculator.removeMachine("stone_separator");
```

`Calculator.addJson(name, mapData)` accepts the full `foundations_calculator:process` JSON schema, including counted/tag ingredients, probabilities, random circuit variants, `value`, `research` and `research_group`. See `KUBEJS.md` for the shared data format. Restart or perform the reload supported by your scripting setup after editing. The release includes a working `.zs` example.

## Power connections

| Power system | Into Foundations | Out of Foundations | Item charging |
|---|---|---|---|
| NeoForge FE | Native sided ports | Native sided ports | Native FE items |
| RF/IF/CF and other FE labels | Through the other mod’s FE capability | Through the other mod’s FE capability | When an FE item capability exists |
| Mekanism J | Native strict-energy port | Native strict-energy port or cable FE path | Both directions, including Foundations powered items |
| GregTech CEu EU | Native voltage/amperage packets | Native voltage/amperage packets | GT electric-item API in both directions |
| Modern Industrialization EU | Native MI port, any cable tier | Native MI port, any cable tier | MI energy API in both directions |
| GrandPower | Native long-valued FE | Native long-valued FE | Long-valued FE items |
| Applied Energistics AE | AE items that permit discharge | FE into AE2 energy acceptors; charging supported AE items | Existing AE item adapter, subject to AE access restrictions |

Foundations stores FE internally. Mekanism follows its FE conversion setting (normally 2.5 J/FE), MI follows `forgeEnergyPerEu`, and GrandPower is 1:1 FE. Whole integer conversion quanta are transferred; sub-quantum leftovers stay in their original storage. For example, 2 FE ↔ 5 J transfers exactly, and an isolated 1 FE is deferred. Simulation never changes stored energy.

Use the wrench or machine configuration to set faces: Auto, Input, Output or Disabled. Processors accept power automatically; generators export; power cubes accept and export. Native ports use the same FE storage and face gates. A cached connection observes face changes. Charging slots also respect each external item’s permissions and transfer limit.

### GregTech version

The integration targets the official **8.0.0-SNAPSHOT+70db06c** Minecraft 1.21.1 build, pinned as `8.0.0-20260928.082845-98` in this source project. It is a development snapshot, not a stable release. [Official JAR](https://maven.gtceu.com/com/gregtechceu/gtceu/gtceu-1.21.1/8.0.0-SNAPSHOT/gtceu-1.21.1-8.0.0-20260928.082845-98.jar); this GT build embeds its required ModularUI, Configuration and Registrate dependencies.

The older public **7.0.2** build fails dedicated-server startup on NeoForge 21.1.250 ([upstream issue #4155](https://github.com/GregTechCEu/GregTech-Modern/issues/4155)) and also references a removed KubeJS class with KubeJS build 379. It is not the supported GT build for R5. Foundations does not patch or bundle GregTech itself.

### GregTech configuration

The default output is **32 EU at 1 amp/tick (LV)**, consuming 128 FE/tick at the usual ratio. The input budget is **4 amps/tick**, shared across all faces. Incoming voltage is accepted only if the whole packet fits the machine’s FE transfer allowance and free capacity. Oversized input is refused without explosions. Output refuses cables or receivers rated below the configured voltage; downstream cable loss is handled by GregTech.

`compat.gtceu.fePerEU = 0` inherits the higher of GregTech’s two conversion ratios, using one symmetric ratio in Foundations. A positive value explicitly overrides it. `euToFE` and `feToEU` enable the two directions. Changing ratios reinterprets the native view of existing FE; change pack balance settings while machinery is stopped.

Set `compat.gtceu.inputVoltage`, `outputVoltage`, `inputAmperage`, `outputAmperage`, `chargerTier`, and `itemTier` as needed. `chargerTier` and `itemTier` default to **1 (LV)**; higher-tier GT batteries need a higher charger tier. Each `machine.<id>.euInputVoltage`, `euOutputVoltage`, `euInputAmperage`, and `euOutputAmperage` overrides its global value; 0 inherits it. Existing FE transfer rates and capacities still apply. These options are in `foundations_calculator-server.toml`; full bounds/defaults are in `CONFIG_REFERENCE.md`.

The machine pushes native packets into GT targets instead of pretending GT has a simulated FE storage API. Connections between two Foundations machines continue to use FE. GT cables may independently provide FE compatibility; a Foundations integration toggle does not disable another mod’s own FE converter.

### Addon API

`FoundationsEnergy.item(stack)` and `.block(level, position, face)` return an `IEnergyStorage` view, following the R6/R7 `power.routing.preferNative` policy. Native-first is the default for external targets; FE-first is explicit. A refusing selected native route does not silently fall through to FE. `registerItem` and `registerBlock` accept additional adapters; return null for unsupported targets and honor simulation, sided permissions and capacity. GT block transport uses `registerSender`/`sendNative`: return consumed FE after a real native packet transfer, or -1 for an unhandled target. Callers debit the source only after the returned acceptance. Do not use GT `changeEnergy` to push into cables.

The optional dependencies are never bundled in the Foundations release JAR. Old IC2/Tesla and Fabric-only energy APIs are not claimed as supported. Create rotation and Botania mana are separate gameplay systems, not electrical units supplied by this bridge.

API references: [GregTech energy container](https://github.com/GregTechCEu/GregTech-Modern/blob/1.21/src/main/java/com/gregtechceu/gtceu/api/capability/IEnergyContainer.java), [Mekanism strict energy](https://github.com/mekanism/Mekanism/blob/1.21.x/src/api/java/mekanism/api/energy/IStrictEnergyHandler.java), [MI energy API](https://github.com/AztechMC/Modern-Industrialization/blob/1.21.x/src/main/java/aztech/modern_industrialization/api/energy/EnergyApi.java), [GrandPower](https://github.com/Technici4n/GrandPower).

## Plants

Ordinary `CropBlock` seeds work automatically. The optional generic path also plants NeoForge `SpecialPlantable` items and surviving `BonemealableBlock` BlockItems. Ordinary mature crops are harvested using their real loot tables. More complex external crops need a `PlantAdapter`; generic compatibility does not invent a harvest contract for every modded plant.

Register `FoundationsPlants.register(adapter)` during common setup. Implement `accepts`, `canPlant`, `plant`, `canHarvest`, and `harvest`. `plant` receives a one-item copy and must not consume the original inventory; the greenhouse performs that commit. `harvest` must remove/reset the crop exactly once and return its drops. Adapters must honor protection rules appropriate to their plants. Chunk loading is checked before calls. `compat.externalPlants` and `greenhouse.acceptGenericPlants` control external adapters; native Calculator crop-tier restrictions remain enforced.

This replaces the extension points needed inside Foundations, not the old Sonar Core binary API. Old 1.12-only integrations require a new addon built against actual 1.21.1 APIs; legacy JARs are not compatible.

## 0.0.2a long-valued energy API

`com.foundations.calculator.api.LongEnergyStorage` is the canonical-FE long-valued addon contract. Use it when an addon must observe or move buffers beyond the int ceiling of NeoForge `IEnergyStorage`.

```java
LongEnergyStorage item = FoundationsEnergy.longItem(stack);
LongEnergyStorage block = FoundationsEnergy.longBlock(level, pos, side);
FoundationsEnergy.registerLongItem("example", stack -> ...);
FoundationsEnergy.registerLongBlock("example", (level, pos, side) -> ...);
```

The contract is still FE-denominated. Native EU/J adapters must convert at their boundary rather than returning native units through this interface. Implementations must honor `simulate`, sided permission and capacity; a successful amount is always the amount actually accepted/extracted in canonical FE.

The older `FoundationsEnergy.item/block` methods remain and return NeoForge `IEnergyStorage`. They are intentionally bounded by that API's `int` method signatures even when the underlying Calculator store is larger.
