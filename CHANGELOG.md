# 0.0.2a.R2 — final 2a upgrade/storage polish candidate

- Fixed Reinforced Furnace Speed and Energy upgrades being exposed in the GUI but ignored by its special smelting path.
- Fixed Analysing Chamber Speed and Energy upgrades being exposed but ignored by its special analysing path.
- Unified effective upgrade cost/time helpers so generic processors and special upgrade-capable processors use the same server-configured math.
- Machine diagnostics now report the same upgraded FE cost the machine will actually consume.
- Added upgrade item tooltips and empty upgrade-slot guidance for Speed, Energy, Transfer and Void behavior.
- Reinforced Chest/Storage bulk menu icons now synchronize with item count 1 while the real bulk quantity remains the separate count below the slot, removing the confusing double-number display.
- Added four native regression GameTests; final runtime floor is 124.
- Retains all R1 long-energy, migration, power-adapter, preset, Field Guide, Patchouli and Guide API work.
