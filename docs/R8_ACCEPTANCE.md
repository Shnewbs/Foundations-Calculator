# R8 local acceptance checklist

Use a copied world and keep the last working installed JAR. R8 is not accepted merely
because the API tests passed. The updater never auto-installs a mod.

## Native build

Run UPDATE_TO_R8.bat (R7/R7-HF1 source) or BUILD.bat in the complete source. Expect
12 successful JUnit tests across IngredientAllocationTest, PowerCoreTest, R7CoreTest,
EnergyApiClasspathTest, GuideApiTest; zero failed/error/skipped. The shared API must be
nested in the main JAR under Jar-in-Jar metadata, not installed as a separate core.
Then run VALIDATE_R8.bat and integrations mode; require110or more required GameTests.

## Reader and content

Open an existing info_calculator item: it is called Calculator Field Guide and no new
registry item is needed. Check10chapters/107entries. Search dirty chip, EU, greenhouse,
and namespaced item IDs. Inspect several long machine pages, including Research,
Atomic Multiplier and Flawless Greenhouse. Check all input/output quantities/chances,
recipe next/previous, shaped crafting positions and item links. Unknown recipes must
say none available, not display an invented default recipe.

Check 1280×720 and windowed1024×768 at GUI scales1–4 where supported; test a320-logical-
pixel-wide GUI for Contents/Read mode. Font should remain native-sized and readable,
not be scaled as an entire low-resolution canvas. Check search focus/Ctrl+F, Done/Esc,
Back/Forward, Alt+arrows, PageUp/Down, Home/End, Saved toggle, scrolling and scrollbar
dragging. No blur/shadow overlay may cover page text or buttons. Tooltips must not
cover controls after the mouse leaves the item. Reopening should retain bookmarks
and the last stable target; reload/missing entries must not crash or delete books.

## Live data / network

On a copied dedicated server change a recipe energy/chance override and EU/J ratio;
restart or use the actual config reload path. Both players/readers see the effective
server values, not their own client preferences. Refresh snapshots. Confirm the
source label and unavailable messages when guide.liveValues=false, no world is loaded,
or an optional integration is missing. A resource reload must not grant research.
Change research status and verify guide research labels; locked recipes still cannot
be executed from the guide. There is no recipe transfer/action endpoint here.

Rapidly change pages while requests are pending, refresh, then disconnect/reconnect
to another world. Old replies must not overwrite new snapshots. Test timeout behavior
and packet bounds. The server must reject unknown/private keys and rate-limit requests.

## API / master interoperability

Build a small development mod with the SDK data-only example and a different installed
owner namespace. It should appear alongside Calculator under All guides. Its link to
Calculator should resolve; removing Calculator must leave a missing link safe in a
separate reader. Override one entry using a client resource pack and F3+T: index/content
must refresh without duplicating books or retaining stale references.

Integrate the SDK master bridge into an actual test reader. Register a compatible
host and verify opening Calculator lands **inside that reader** at the requested
entry, including recipe/live resolution through the public API. Repeat with missing
capabilities, apiVersion mismatch, a declined request, an exception and removed host:
standalone should open. Set client guide.preferMasterReader=false to force standalone.
Test two independently packaged mods embedding the same API coordinates so the loader
selects one compatible library rather than two independent registries. This native
co-loading test is still outstanding, not implied by the JVM contract tests.

## World content regression

Build both greenhouse diagrams in all4horizontal facings. Check stair orientations,
controller-relative axes, floor/water/crop requirements and protected denied cells.
Test default Dynamic structure and altered radius using the explained generic rules;
Flawless lengths1/3/larger; locator platform radius1/larger; conductor mast orientation.
Keep R7 automation gates/owner safeguards and R6 conversions/losses intact. Check dirty
chip processing, research, old recipes, models in hand, restart and dismantle recovery.
No test here authorizes destructive changes to an existing live world.
