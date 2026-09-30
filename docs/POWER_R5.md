# R5 power connections and model fix

Use Minecraft 1.21.1, NeoForge 21.1.250 and Java 21. Replace the earlier Foundations Calculator JAR on client and server. The core is still embedded; no extra Foundations/Sonar Core mod is needed.

## Supported electrical systems

- **FE** natively, including RF/IF/CF-labelled mods that expose NeoForge FE.
- **Mekanism J**, including native block ports and chargeable items; follows Mekanism’s configured ratio (normally 2.5 J = 1 FE).
- **GregTech CEu EU**, using real cable voltage/amperage packets and battery APIs.
- **Modern Industrialization EU**, following MI’s `forgeEnergyPerEu` setting.
- **GrandPower** long-valued FE.
- **AE2** powered-item charging through its AE API; AE2 networks use their usual FE acceptors.

Optional mods are not included in the Foundations JAR. The validated release list and detailed adapter contracts are in `INTEGRATIONS.md` in the source ZIP.

## GregTech build

R5 uses the official Minecraft 1.21.1 **8.0.0-SNAPSHOT+70db06c**, pinned build **8.0.0-20260928.082845-98**. This is a development snapshot. Download it from the [official GT Maven](https://maven.gtceu.com/com/gregtechceu/gtceu/gtceu-1.21.1/8.0.0-SNAPSHOT/gtceu-1.21.1-8.0.0-20260928.082845-98.jar). The tested GT JAR SHA-256 is `539f43e6c4a8010bc09abe3851d635ec9b2c9065508c420e87fb9bededbf815d`. Its required ModularUI, Configuration and Registrate dependencies are embedded by GT.

The older 7.0.2 public build has an upstream dedicated-server startup crash and does not work with the tested current KubeJS build. It is excluded by R5’s optional dependency range. No GregTech JAR is needed if you are using the other power systems.

Default EU output is **LV: 32 EU at 1 amp/tick**. Default input allowance is **4 amps/tick**, subject to FE port capacity and rate. Over-voltage output is refused; excessive incoming packets are refused without an explosion. GT cables apply their normal energy losses. Foundations-to-Foundations links retain native FE behavior.

## Configuration

The server configuration now contains **4,164 settings**. Change these in `foundations_calculator-server.toml`, or through the config screen with the appropriate server access:

| Key | Default / meaning |
|---|---|
| `compat.gtceu.enabled` | `true` |
| `compat.gtceu.euToFE`, `feToEU` | `true`, enable each direction |
| `compat.gtceu.fePerEU` | `0`, inherit the higher of GT’s two ratios; positive values fix a symmetric ratio |
| `compat.gtceu.inputVoltage` | `2147483647`, accept any whole packet that fits the FE input limit |
| `compat.gtceu.outputVoltage` | `32`, LV |
| `compat.gtceu.inputAmperage` | `4`, shared across all faces |
| `compat.gtceu.outputAmperage` | `1`, shared across all faces |
| `compat.gtceu.chargerTier`, `itemTier` | `1`, LV; raise the charger tier for higher-tier GT batteries |
| `machine.<id>.euInputVoltage`, `euOutputVoltage`, `euInputAmperage`, `euOutputAmperage` | `0`, inherit the global value |
| `compat.mekanism`, `modernIndustrialization`, `grandPower`, `ae2` | `true`, enable each bridge |

Existing per-machine FE capacity, transfer and charging rates still apply. Use **Sides** or the wrench to set Input, Output or Disabled faces. Processors receive automatically, generators export, and cubes do both. Charge/discharge slots honor the external item’s access rules.

Conversions move only whole representable units. Small leftovers stay in storage instead of being rounded up into free energy. At 2.5 J/FE, a transfer quantum is 5 J ↔ 2 FE. A full buffer or an undersized packet can therefore defer a transfer.

## Oversized models

All 150 building block items now have explicit transforms for both hands, ground, GUI, item frames and head display. Tall/wide animated models are centered and scaled to fit one item cube. Their placed multiblock dimensions are preserved.
