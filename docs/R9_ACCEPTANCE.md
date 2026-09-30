# Foundations Calculator 0.0.1a.R9 — acceptance checklist

R9 becomes the frozen `0.0.1a` baseline only after all gates below pass.

## Build and tests

1. Run `BUILD.bat`; `clean test build` must complete successfully and produce `build/libs/FoundationsCalculator-0.0.1a.R9.jar`.
2. Run `VALIDATE_R9.bat`; the standalone GameTest server must positively report the required suite passed.
3. Run `VALIDATE_R9.bat integrations` with the pinned optional integrations/KubeJS available; preserve fresh logs.

## Field Guide visual acceptance

Test the native book in windowed and fullscreen play at several Minecraft GUI scales.

- soft-grey leather shell is centered and fully inside the viewport;
- parchment, center binding and chapter tabs remain crisp;
- no text crosses the binding or cover;
- Welcome and First 10 Minutes open and read naturally;
- search, chapter filtering, Saved, Back/Forward, Refresh and Research all click where rendered;
- narrow layouts switch between Contents and Read without clipped controls;
- long entries scroll inside the page rather than scaling the whole book;
- notes/tables/live settings are visually distinct;
- live settings show friendly labels/units, not raw configuration keys;
- raw entry IDs are not shown during ordinary reading;
- diagrams, item icons, recipe paging and tooltips remain usable.

## Field Guide item

- `foundations_calculator:info_calculator` is still the one guide item and recipe target;
- inventory, hand, dropped-item and item-frame views clearly read as a soft-grey leather book;
- right-click opens the same guide and no duplicate Patchouli book item appears.

## Reader interoperability

- without Patchouli/master host: native reader opens;
- with Patchouli: generated `foundations_calculator:field_guide` resources load without adding a second book item;
- a compatible Guide API host can accept the exact Calculator guide/entry target;
- removing/declining the host falls back to the native reader;
- reading any reader never grants research.

## Regression

Recheck dirty-chip processing, Research Chamber, calculators/modules, machine power, automation/protection, greenhouses, recipes, JEI/EMI/Jade, KubeJS/CraftTweaker and copied-world restart. R9 should not change gameplay/recipe results from R8.1-HF1.
