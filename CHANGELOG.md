# 0.0.4a — native long transfer scaling

- Native long machine ports honor transfer multipliers above 2,147,483,647 FE per operation.
- Standard NeoForge FE calls remain int-bounded; existing defaults and direction gates are retained.
- A zero transfer multiplier now fully disables transfer and charging instead of leaving a one-FE floor.
- Config diagnostics report standard FE and native long rates separately.
- Added native regressions for large simulated/executed input and disabled faces (127-test floor).
- Retains centralized machine defaults, Guide API runtime classpath and guarded FE registration priority.

Platform: Minecraft 1.21.1. Processing costs, item charging and ordinary FE push routes remain int-bounded. No claim of universal 64-bit operation scaling or 26.3 compatibility. Manual world/client/performance acceptance remains pending.
