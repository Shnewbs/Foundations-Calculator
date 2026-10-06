# 0.0.5a.R1 — high-capacity energy balancing

- Fixed automatic power-cube balancing when capacity/energy cross-products exceed the signed-long range. Saturating both products previously erased their difference and stopped transfer.
- Uses exact arithmetic for oversized stores, retaining allocation-free paths for ordinary storage and banks with matching capacities.
- Added five JUnit regressions, including 5,000 deterministic oracle comparisons, and a native high-capacity cube transfer regression (131-test floor).
- Includes all prior 0.0.5a, 0.0.4a and 0.0.3a-dev.1 changes.

Platform: Minecraft 1.21.1 / NeoForge 21.1.250. This revision is on the path to 0.1a/0.1b; it does not include a working 26.3 port. Manual world/client/performance acceptance remains pending.
