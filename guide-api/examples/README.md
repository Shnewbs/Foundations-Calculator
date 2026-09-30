# Publisher and master-reader examples

The API version is **1**, Maven artifact `com.foundations:foundations-guide-api:1.0.0`.
The API has no Minecraft/NeoForge/Calculator imports. Java 21 is required.
These examples are not a finished master-guide mod, and are not automatically installed.

## Data-only publisher

Copy `data-only/assets/example_guides/foundations_guides/` into a mod whose ID is
`example_guides`. Change **all** owning namespaces/IDs for your real mod. That owner
must actually be installed. A resource pack alone cannot impersonate an absent mod.
The same structure can be published by an installed mod without compiling against
Calculator. For a resource-pack-only experiment, use the already-installed
`minecraft` namespace consistently, `owner: minecraft`, and a unique book ID.
Use resource-pack format **34** for Minecraft 1.21.1 assets. This example is a mod
resource directory, not a resource-pack archive.

## Java master reader

`ExampleMasterBridge.java` compiles against only the public API JAR. It delegates to
an injected renderer, passes the actual immutable Book/Entry data, and registers a
host only during the client's setup. Implement **all** advertised block capabilities
before claiming them. Return true only after displaying that entry inside your own
reader. Returning false preserves Calculator's standalone fallback.

On client resource reload, discover all `foundations_guides/**/*.json` through
Minecraft ResourceManager (respect pack priority), call `GuideParser.scan`, then
`GuideApi.install` on the client thread. Calculator already supplies this loader;
multiple consumers may install the same snapshot idempotently. A standalone master
must provide its own loader when Calculator is absent. Do not scan every tick.

For live blocks, call `GuideApi.live(new LiveRequest(target, owner, key))`; do not
invoke Calculator internals. The publishing mod owns server requests and returns
READY/PENDING/UNAVAILABLE plain text. Poll PENDING slowly and never force READY by
substituting an assumed value. Never access another player's inventory or UUID.

The actual contract tests create a second guide and a test reader, check compatible,
incompatible, declining, failing and removed hosts, then consume Calculator's
structured content without importing its Screen or other internal classes.
That proves API-level interoperability, **not** an in-game master UI acceptance run.

Recipe blocks likewise resolve through `GuideApi.recipes(new RecipeRequest(target,
owner, block.target(), page, block.options().getOrDefault("profile", "")))`. Render
its portable `blocks()` and expose previous/next using `count()` and `page()`.
Do not import `ProcessRecipe`, `GuideRecipes` or any Calculator screen in a master.
