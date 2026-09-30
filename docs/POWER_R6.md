# R6 power configuration and diagnostics

**Release status:** source candidate; local native build and in-game validation still required. FE remains the canonical stored energy. External J/EU/AE units are converted at the adapter boundary. Optional APIs are compile-only; no separate core or bundled optional mod is added.

## Ratio settings (server side)

| System | Exact key | Default | Meaning |
|---|---|---|---|
| GregTech | `compat.gtceu.fePerEU` | `0` | 0 inherits the higher of GT's two FE/EU ratios; positive integer is FE per EU. |
| Mekanism | `power.mekanism.joulesPerFE` | `0.0` | 0 inherits Mekanism; positive decimal is J per FE. Example 2.5 means 5 J : 2 FE. Mekanism's own disabled FE-conversion setting is still respected. |
| Modern Industrialization | `power.modernIndustrialization.fePerEU` | `0.0` | 0 inherits `forgeEnergyPerEu`; positive decimal is FE per EU. |
| AE2 items | `compat.aeEnergyToFE` | `2.0` | Existing Calculator FE per AE setting; retained, not renamed. This is not a native AE2 grid connection. |
| FE / GrandPower | fixed base | `1:1` | Both already represent FE. There is no artificial exchange multiplier between the same unit. |

R5's older ratio keys still work. R6 does not add a competing GregTech ratio key, and changing voltage does not change the EU/FE exchange rate.

Use `foundations_calculator-server.toml` in the world's NeoForge server-config location. For a repeatable setup, stop the server before editing and restart it. Pack defaults go in `defaultconfigs`. The SERVER spec synchronizes gameplay settings to clients; client-side edits cannot override the server. `/reload` is for data packs and is not a universal TOML-reload command. A live configuration update delivered by NeoForge is observed by the cached adapter views on their next operation.

## Consistent policy keys

Each system uses `power.<system>.`, where `<system>` is `fe`, `gtceu`, `mekanism`, `modernIndustrialization`, `ae2`, or `grandPower`.

| Suffix | Default | Meaning |
|---|---|---|
| `blockPorts` | true | Enable that API for machine connections and external block adapters. Omitted for AE2 because it has no native grid port. |
| `itemCharging` | true | Enable charging AND discharging via that API's item adapter/exposed powered-item capability. |
| `input` | true | Native energy INTO Foundations FE storage; includes draining an external battery. |
| `output` | true | Foundations FE OUT into that native API; includes charging an external battery. |
| `inputLossPercent` | 0 | Integer 0–99 percent loss on input; omitted for direct FE. |
| `outputLossPercent` | 0 | Integer 0–99 percent loss on output; omitted for direct FE. |

Scope and direction are independent. Disabling an integration's block ports does not disable its item charging. Disabling the advertised FE capability does not disable the underlying store used by native EU/J adapters or internal machine recipes. Machine profile capacity/rate, side modes, native item charge rules, and native voltages/tiers still restrict each transfer. Existing `compat.*` masters and GT `euToFE`/`feToEU` switches must also permit the operation.

GrandPower optionally supports loss policies despite its fixed 1:1 base, because it is a separately selected adapter. With nonzero losses, use explicit native routing; its direct FE alternative is still a different API. Leave both losses at zero for ordinary long-FE interoperability.

### Custom EU example

```toml
[compat.gtceu]
enabled = true
euToFE = true
feToEU = true
fePerEU = 4
outputVoltage = 32
inputAmperage = 4
outputAmperage = 1

[power.gtceu]
blockPorts = true
itemCharging = true
input = true
output = true
inputLossPercent = 10
outputLossPercent = 20

[power.mekanism]
joulesPerFE = 2.5
blockPorts = true
itemCharging = true
input = true
output = true
inputLossPercent = 0
outputLossPercent = 0

[power.modernIndustrialization]
fePerEU = 4.0
blockPorts = true
itemCharging = true
input = true
output = true
inputLossPercent = 0
outputLossPercent = 0

[power.routing]
preferNative = true
```

These are examples, not the shipped settings. Prefer leaving ratios at inherit unless the pack has a deliberate, consistent exchange policy. Do not paste a second duplicate table into an existing TOML; edit the existing section instead.

## Exact transfer and losses

A lossless 2.5 J/FE ratio uses the quantum 5 J : 2 FE. Offers smaller than 5 J cannot create 1 FE by rounding. The remainder stays in its original store. For input loss 10%, the corresponding input quantum is 25 J -> 9 FE.

For a base 4 FE/EU, 10% input loss and 20% output loss, 100 EU enters as 360 FE and that FE can leave as 72 EU. The losses are independent, but there is still one base exchange rate; they cannot produce a profitable round trip within this adapter.

Long-valued APIs use exact integer quanta. Extremely precise ratios or losses may require a quantum larger than the item's rate, the machine's rate or available room; the transfer then pauses instead of creating energy. Increase the applicable rate/capacity or use a simpler ratio. Invalid/unrepresentable settings fail closed and appear in the power overview. Ratio plans are rebuilt when settings change, not allocated with BigDecimal on every transfer.

GT block networks are packet-based: one incoming 32 EU packet at 4 FE/EU with 10% input loss credits **115 FE** (rounded down); the same outgoing packet with 10% output loss costs **143 FE** (rounded up). At 20% output loss it costs 160 FE. Packets whose loss-adjusted cost cannot fit the integer FE budget are refused. Voltage/amperage limits and the shared per-tick amp budgets remain in effect. GT item APIs use the normal exact-quanta path, plus their tier/rate checks; `changeEnergy` is a storage API rather than a network packet transaction.

AE2's item API supports fractional AE. FE credits are rounded down and FE debits conservatively up. All simulations remain nonmutating under the respective API contracts. A misbehaving third-party capability that mutates during a simulation is outside Calculator's control.

## Routing and converter-loop warnings

`power.routing.preferNative = true` is the new default for **external** items and block targets. It selects Calculator's native adapter before a generic FE facade. A present but disabled/refusing native adapter does not then fall through to FE. Set `false` to deliberately prefer FE where advertised; native settings do not govern an FE facade in that mode.

Calculator-to-Calculator links stay FE and avoid converting at both ends. GT cable output always follows its voltage-aware sender even when generic FE-first routing is selected, so FE cannot be used to sidestep GT voltage safety.

An external source pushing into Calculator chooses which advertised capability it uses. Calculator cannot force another mod to use J/EU rather than that mod's own FE path. Disabling `power.fe.blockPorts` closes the direct FE capability of Calculator machines without closing native EU/J capabilities. It also prevents Calculator's generic FE block sends. That is an optional enforcement tool, not a required default.

The overview warns when a Calculator GT/Mekanism/MI override differs from the other mod's known ratio, and when GT itself has differing direction ratios. This is a configuration warning, **not** a global proof that no converter loop exists elsewhere in the pack. Align other converters, use inherit, or avoid incompatible routes. Changes to exchange rates do not rewrite already-stored FE into a new unit; changing rates with charged buffers can change their native-equivalent value.

## In-game diagnostics

```text
/foundations power
/foundations power inspect
/foundations power item
/foundations power inspect <x> <y> <z>
```

The overview lists installed adapters, effective ratio, inherited/override source, scopes, directions, losses and mismatch warnings. `inspect` looks up to eight blocks along the player's view and reports that Calculator machine's status, raw FE, rates, GT packet limits and six faces. An input-only face reports that the incoming source chooses the API; it does not pretend to know the source's last transfer. Outbound reports name the selected adapter and distinguish disabled inputs, empty source, full receiver, too-small quantum, excessive voltage and packet limits. Generic receivers are simulated, never executed; GT reports advertised packet readiness without transmitting a packet.

Explicit coordinates require operator permission level 2. The ordinary look/item commands do not alter the world. Reports refuse unloaded chunks. `power.diagnostics.enabled` can disable them; `cooldownTicks` defaults to 20 ticks per player. A text-width **Power** button in energy-machine screens requests the same server report, closes the menu, and displays it in chat. No persistent transfer-history database or per-tick neighbor diagnostic scan is added.

## Dependency and acceptance boundary

Use the unchanged R5 dependency targets in `build.gradle` and [POWER_R5.md](POWER_R5.md). GT's pinned development snapshot is not a new stable release, and an unavailable upstream artifact can prevent a fresh build. The full native build, both standalone and optional-mod GameTests, and a copied-world client smoke test still need to pass for R6. See [R6 validation](../validation/r6/RESULTS.md).
