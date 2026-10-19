# Published 0.1.0-rc.1

All three targets are published as GitHub prereleases and CurseForge **beta** uploads. RC is the candidate stage; it is not a stable release. Validation finished on 2026-10-19 UTC. [Changes, original 1.12.2 comparison and known limits](RC1_AUDIT.md).

| Minecraft | Version | Native checks | Real client | CurseForge file |
|---|---|---|---|---|
| 1.21.1 | `0.1.0-rc.1` | 38 units; 154 standalone + 154 full-integration GameTests; 154 on NeoForge 21.1.219 | Six positive markers, 23 screenshots | 9252180 |
| 26.1.2 | `26.1.2-0.1.0-rc.1` | 48 units; 240 standalone + 240 matching-integration/restart assertions; 240 on minimum 26.1.2.100 | Names, model submissions, output-page packets; two screenshots | 9252189 |
| 26.3 | `26.3-0.1.0-rc.1` | 48 units; 240 standalone + 240 matching-integration/restart assertions; 240 on minimum 26.3.0.39-beta | Names, model submissions, output-page packets; two screenshots | 9252179 |

The new 20-assertion tool/module regression runs inside each native runtime suite. It checks cancellation before placement, exact accepted costs, permission callbacks changing resources or target state, disabled nutrition/portable calculation, 1,000 idle smelter calls, progress reset and storage-selection invalidation. The 1.21.1 full integration suite includes KubeJS/Rhino and technology integrations; 26.x supports its own matching JEI/Jade/AE2/GuideMe set and datapacks rather than claiming KubeJS support.

Actual 26.1.2 and 26.3 color screenshots were inspected: Extraction/Precision frames remain neutral gray, Research Chamber retains its green window, and Reinforced Chest retains its dark texture. Main-client world models, animated chest and the native output page were also inspected. The R6 red/black/white regressions have not returned.

## Immutable release inputs and receipts

| Minecraft | Source release commit | Binary SHA-256 |
|---|---|---|
| 1.21.1 | `55ab27307bec7804be06bb7e7c41a4de0e1146c4` | `79efdf533341117dc026a676a10548d82a5391e1bcc79ffe9a0dfbb1996df960` |
| 26.1.2 | `d93c6720fa017c27251575de29194790cd1b106d` | `bee9f1c76eb50304cfd4dd55e0396f070ef5cfe4de1eaef2f43c5f264b7df05a` |
| 26.3 | `225959e47fca66a020f357dc185babd54364c247` | `63368c7611b8f5955ca222645b6e71f3fc0a0f039bae375e2c33512bc7741b13` |

Downloaded binary bytes were hashed and matched against both the release audit and accepted CurseForge receipt for each target. Each [GitHub release](https://github.com/Shnewbs/Foundations-Calculator/releases) contains the binary, sources JAR, `release_checks.json` and `curseforge-upload.json`. All three receipts identify project 1734096 and release type beta. Documentation-only commits after these source commits do not change the released binaries.

Passing runs: [main native/integrations](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/39195423746), [main real client](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/39195423704), [26.1.2](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/39195682488), [26.3](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/39195434001), [main publisher](https://github.com/Shnewbs/Foundations-Calculator/actions/runs/39195733614).

The first 26.1.2 attempt caught a wrong break-event class in the new helper. It was corrected before publication; the release is based only on the successful commit and run above.

## Scope still open

Full cumulative beta/stable acceptance remains incomplete. The main build audit is generated before the separate client/cross-port jobs, so its cumulative `beta_acceptance` fields remain pending; the passing client/port evidence is linked above, not retroactively fabricated in that audit. Copied user-world migration, exhaustive survival/multiplayer/protection-mod playthroughs and long-duration large-farm/network profiles remain open. Original Fabrication's connected-storage interface remains a documented parity difference. The RC label is not evidence that these broader gates passed.
