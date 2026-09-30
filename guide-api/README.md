# Foundations Guide API 1.0.0 — R8 SDK

**Independent Java 21 contract. Not a second required core mod and not a finished master-guide mod.**
Calculator embeds the shared library through NeoForge Jar-in-Jar. Publishers can use
static resources without a Java dependency. Dynamic publishers and master readers use
these exact coordinates and version bounds:

```groovy
repositories { maven { url = uri('guide-api/repository'); content { includeGroup 'com.foundations' } } }
dependencies {
    jarJar(implementation('com.foundations:foundations-guide-api')) {
        version { strictly '[1.0,2.0)'; prefer '1.0.0' }
    }
}
```

The SDK supplies a local Maven repository to avoid requiring a new hosting service.
A publisher can vendor that repository or publish **these identical bytes** under the
same coordinates on a controlled Maven repository. Do not give a modified API the same
version; increment appropriately. Never relocate/shade/copy API classes into your main
mod: that creates separate class identities and registries, breaking discovery.
The library manifest marks `FMLModType: LIBRARY` and a stable automatic module name.
Co-installed Jar-in-Jar selection and standalone development runtime still require
native NeoForge acceptance; isolated JVM tests do not certify the loader.

## Lifecycle

1. Load resources through the client resource manager at reload, preserving resource-pack precedence.
2. Call `GuideParser.scan(Map<namespaced-resource-path,JSON>, ownerInstalled)` off-thread if desired.
3. Call `GuideApi.install(catalog)` on the client thread. Identical snapshots do not change revision.
4. Register live providers (`registerProvider(owner, function)`) and host readers during client setup.
5. Open a stable `Target` via `GuideApi.open(target, standaloneFallback)`.
6. Remove a host/provider when it is intentionally disabled. A declined, missing,
   incompatible or throwing host does not suppress standalone fallback.

All API registry/lifecycle calls are client-thread-only by contract. The Java-only
library can load on a dedicated server but no client resources/UI are initialized there.
Content data are immutable; `GuideSearch` builds an index once per catalog revision.
`GuideNavigation` bounds history/bookmarks at 128 each. `GuideApi.revision()` signals
catalog reloads. Dynamic values are not cached by the API; each publisher owns its
bounded, session-scoped cache and network protocol.

## Schema 1

Root: `assets/<owner>/foundations_guides/<book>/book.json`. See examples for exact shape.
A manifest declares `schema`, namespaced `id`, `owner`, title, item icon, revision,
landing entry, chapter file names and entry file names. Files must stay under the book
root and use safe relative lower-case JSON paths. Optional `required_mods` hides an
unavailable whole book or entry; chapters remain for remaining entries. The landing
entry must remain available. Every referenced chapter must exist.

Entry fields: namespaced `id`, `chapter`, title, integer order, item IDs, search
keywords and structured `blocks`. Search uses labels/body/rows/items/keywords.
A link is `guide_id#entry_id`; a same-guide link may be only `entry_id`.
An empty entry after `#` means that book's landing. Missing cross-guide links remain
safe and explain unavailability in the reader. Bookmarks store IDs, not item stacks.

### Standard blocks

| Type | Fields and behavior |
|---|---|
| paragraph / heading / callout | `text`; plain literal text, no executable markup. |
| steps / list | Optional title in `text`; `rows`, each an array of plain strings. |
| table | Optional `text` caption and `rows`; readers must wrap rather than discard cells. |
| item | `target` namespaced item ID, optional label `text`, options `count` string. |
| recipe | `target`: `machine:<family>`, `item:<item-id>`, `uses:<item-id>`, or `id:<recipe-id>`; optional `options.profile` Calculator machine/tool ID. Query meanings belong to the publisher: masters call `GuideApi.recipes` and render the returned portable blocks; no Calculator recipe classes are needed. |
| image | `target` namespaced texture resource, text/alt caption, string `options.width/height` (max1024); use packaged resources, not external URLs. |
| link | `target` stable entry link, `text` readable label. |
| layers | Caption `text`; each `rows` element is one string of cell symbols; `options.legend` explains every symbol. No generated world modification or hologram is implied. |
| live | `target` is book owner; `options.key` read-only provider key. Label/state comes from publisher; no arbitrary reflection or command execution. |

Unknown block types must include a useful `fallback` string; the parser turns them into
an explicit text callout. Unsupported schema versions, duplicate IDs, missing files,
invalid owner references and malformed JSON reject the affected guide with diagnostics.
Java Book constructors enforce that capability declarations match their actual blocks.

## Bounds and authority

Maximum 128 books; 64 chapters/512 entries per book; 128 blocks per entry; 128 rows per
block; 32 cells per row; resource file 96 Ki characters; total 16 Mi characters. JSON
nesting24/nodes16384; duplicate keys, trailing input, unsafe paths and unpaired Unicode
are errors. Diagnostics are bounded. The reader accepts no embedded commands or URLs.

Static prose/art is public client resource data and is resource-pack-overridable. It is
not automatically server-synchronized or an access-control boundary. Use a server
resource pack to distribute server-specific static prose. Calculator gameplay values
come from its SERVER config, normal recipe synchronization or its explicit bounded
snapshot channel. Reading/book ownership never grants research or bypasses recipes.

No universal claim about unrelated third-party books: those need a publisher/adaptor.
Legacy books are not deleted, unregistered, or silently migrated. The item-opening hook
can redirect to an accepted master without requiring all individual books in inventory.
Automatic starting-book suppression is not part of R8; pack authors can control grants.

## Build and verify

From the Calculator source root: `python tools/rebuild_guide_api.py --check --test`.
Rebuild after intentionally modifying API sources with `python tools/rebuild_guide_api.py`.
The Gradle build verifies source/artifact hashes without requiring Python. API changes
must retain backward-compatible contracts within major version1 or get a new major.
The `-sources.jar` and Java tests are supplied for implementation and auditing.

Primary implementation references: NeoForge 1.21.1 networking/resource reload docs and
ModDevGradle's Jar-in-Jar dependency documentation. API-only Java checks were executed;
full NeoForge co-loading and graphical reader smoke tests are outstanding.

## Recipe resolvers and shared snapshot cache

Publishers register `GuideApi.registerRecipes(owner, resolver)`. A reader passes a
`RecipeRequest(target, owner, query, page, profile)` to `GuideApi.recipes` and receives
`RecipePage(state,label,count,page,blocks)`. Results use ordinary text/table/item/etc.
blocks, never Minecraft ItemStack or a publisher's recipe implementation. The publisher
resolves quantities, alternatives, research state, costs and per-recipe overrides.
Ownership is checked, absent/failing providers produce UNAVAILABLE, and nested
recipe/live results are refused to prevent recursive provider expansion. Pages are
bounded at 256 blocks and 2048 recipe matches. A static publisher without a resolver
may provide prose/crafting instructions instead. Another publisher registers its own
resolver rather than requiring a dependency on Calculator's recipe system.

`GuideSnapshotCache` is an optional loader-neutral helper actually used by Calculator.
It accepts an opaque session identity, monotonic nanoseconds and a caller transport.
It limits requests to one per 1.1 seconds, state to 128 keys, waits at most8seconds,
never resets correlation tokens, and rejects late/unknown/wrong-session replies.
Refresh invalidates pending tokens. A snapshot is labelled for freshness, not permanent
truth. The mod supplies its own server authorization and packet-size enforcement.
