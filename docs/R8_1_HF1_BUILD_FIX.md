# Foundations Calculator 0.0.1a.R8.1-HF1 — GuideScreen compile repair

This hotfix changes only `GuideScreen.java` and does not alter guide content, Patchouli resources, recipes, gameplay, power settings, networking, or the Foundations Guide API contract.

## Corrected native compile errors

1. The private helper named `rebuildWidgets()` collided with Minecraft `Screen.rebuildWidgets()`, whose access is protected. The helper is renamed `reinitWidgets()` so it is not an override.
2. `Component.literal(...)` was stored in a `Component` variable before calling `withStyle(...)`. The local now retains the returned mutable component type (`var`), on which `withStyle(ChatFormatting.BOLD)` is available.

The deprecation warnings visible in the supplied build log are warnings, not the cause of the failed compile.

## Local acceptance

Run `BUILD.bat` or:

```bat
gradlew.bat clean test build --console=plain
```

The build must pass `compileJava`, `compileTestJava`, tests, and final JAR assembly. Keep the currently working installed JAR until this succeeds.
