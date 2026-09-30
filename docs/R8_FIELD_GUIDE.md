# Foundations Calculator 0.0.1a.R8 — Field Guide

**Source candidate. The shared Java API library compiled, but the complete Minecraft
mod has not been natively compiled or run in this build environment.** See the
validation report. R7 automation/protection and R6 power settings are retained.

## Use the book

The existing `foundations_calculator:info_calculator` item is now labelled
**Calculator Field Guide**. Its registry ID and crafting recipe are unchanged. Open
an existing one, craft it through the existing recipe, or in an operator test use
`/give @s foundations_calculator:info_calculator`. No second book item is registered.

The guide contains **10 chapters, 107 entries, 45 individual machine pages, 13
module pages, and about 12,800 words**, plus real item/recipe references. Coverage:
getting started; calculators/modules; circuit processing; power; individual machines;
multiblock construction; farming/nutrition; research/mastery; troubleshooting;
pack-author configuration and API publishing. The item index links all 300 public
inventory entries represented by the port catalog; its three crop block entries use
seed/fruit items instead. Not every decorative item needs a redundant full chapter.

Search is full text plus item IDs/keywords. Click a result, chapter-cycle button,
item link, cross-reference, or recipe page controls. The left pane can show This guide,
All guides, or Saved entries. Back/Forward and Alt+Left/Right traverse history.
Ctrl+F focuses search; PageUp/PageDown/Home/End move through a long entry. The page
scrollbar supports clicking and dragging. At narrow GUI widths, Contents/Read switch
panes instead of shrinking the font. The guide does not pause a running world.

Saved entry IDs and last-read history target use
`config/foundations/guide_preferences_v1.json`. No research, player UUID, inventory,
or server addresses are persisted in that preference file. Bookmarks/history are
bounded at128; missing entries are safe, not an excuse to delete user books.

## Actual recipes and settings

Recipe pages query the current recipe manager and Calculator's recipe policies,
including per-recipe enable/energy/time/chance overrides. They show input quantities,
representative ingredient alternatives, circuit-state requirements, outputs/chances,
research restrictions and effective unupgraded profile costs where meaningful.
Program-specific rules are labelled separately. Generic crafting pages include grid
coordinates for shaped recipes. These pages do not craft, withdraw items or unlock
research. Research remains in the existing server system and Research screen.

Live blocks use explicit read-only server requests. Exposed keys cover effective
power adapters/ratios/losses, allowed machine/module configuration, farming/protection,
and the requesting player's own research/mastery summary. Unknown/private keys are
rejected; there is no arbitrary config lookup, reflection, command execution, world
scan, coordinates or inventory endpoint. Requests are server-throttled to one per20ticks.
Responses are bounded (96 lines;512 characters each;24576 total). Client state is
session-bound, token-correlated, capped at128keys and times out after8seconds. A changed
session/refresh rejects old replies. Configuration/research changes invalidate local
cached values on next read; **Refresh** explicitly requests a fresh snapshot.
Unavailable values say so and do not substitute made-up defaults.

Server config adds `guide.liveValues = true`; false leaves static documentation
readable without live snapshots. This is now **4,210 server keys**, with every R7 key
and default retained. Client config adds `[guide] preferMasterReader = true`.
Existing guide text uses labelled defaults where an example is needed. Static prose
is client resource data; server-specific prose must be distributed by a resource pack.

## Construction diagrams

Basic and Advanced greenhouse structural layouts are exported from the actual
`GreenhouseBlueprint` production class and verified across its four horizontal
orientations in isolated coordinate tests. The book shows layer grids, controller-
relative axes, materials and clearances. Basic:118 structural blocks; Advanced:344.
Controller, floor/soil, irrigation and seeds are separately explained. Flawless
(length3 example), default-radius Dynamic structure, locator (radius1 example) and
conductor mast have labelled construction diagrams. They are documentation, not an
unvalidated world-placement/hologram feature. Nondefault dimensions follow the code's
rules and the server settings rather than pretending the example is universal.

## Shared API / future master book

`guide-api/README.md` is the contract and SDK. It is a standalone Java21 library,
**not a required extra Core mod**. Calculator embeds it through Jar-in-Jar under
`com.foundations:foundations-guide-api:1.0.0`. Publishers/readers use the same compatible
major-version range, not shaded copies with isolated static registries.

Static publishers contribute schema1 resources in their own installed mod namespace.
Java publishers may register owner-scoped live and recipe resolvers. Recipe resolvers
return portable text/item blocks, so a future master does not need Calculator's
private recipe classes. A compatible host receives the same immutable guide entries
and only suppresses standalone opening after it reports success. Missing, disabled,
outdated, declining, or failing hosts fall back. A custom host must actually render
all capabilities it advertises. Ordinary books are never deleted or unregistered.

Calculator's All guides view can read participating resource guides already. The
**separate master-guide mod and unrelated third-party book adapters are not built in
this update**. The SDK includes a data-only second publisher, a public-API master
bridge example, and executed pure-Java independent-consumer tests. These are not a
claim that an in-game master UI/co-installed library negotiation was tested.

No auto-removal of starting books or crafting recipes is added. Pack owners may
control grants externally. Book ownership never grants research. Translations beyond
English and old-world migration remain outside the requested scope.

## Build / acceptance

R8 changes the network protocol to5: update Calculator on both client and server.
Use the updater on R7 or R7-HF1 source (folder names may still say R6). The updater
includes the HF1 real-NeoForge test classpath correction. It can run outside the project,
checks affected file hashes, backs up originals, supports source rollback, and offers
`clean test build`. No JAR is automatically installed and no world/config is edited.

Expected new mod: `build/libs/FoundationsCalculator-0.0.1a.R8.jar`.
The standalone API JAR in the SDK is **not** that mod and is not an extra gameplay mod
to install in your mods folder. Keep the current working installed mod until local
build, GameTests and copied-world client checks pass.
