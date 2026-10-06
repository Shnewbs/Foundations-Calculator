# Build toward 0.0.5a

Requested scope: keep 1.21.1 supported, port to 26.3, prepare for 26.4, and publish every completed update through GitHub Releases.

## Current checkpoint: 0.0.3a-dev.1, compiled in GitHub Actions

Implemented source changes: R2 documentation and generator normalization; dynamic diagnostic version; immutable centralized input/output/battery/upgrade/power defaults; configuration-aware rates retain their prior overrides; targeted JUnit tests; tagged prerelease workflow gated on build, standalone and integration GameTests and release audit.

Checks actually completed: Python asset validation and structural progression validation. Both passed before the machine-profile refactor. Guide/config regeneration retained 109 entries, 910 recipes and 4272 settings.

Updated validation: GitHub Actions passed the native build and JUnit suite. The first standalone run passed 123/124 tests; the remaining shared Guide API load test exposed a missing development-runtime classpath declaration, now corrected without weakening the test. A rerun is required. Five profile JUnit tests also passed independently. Local arithmetic/policy assertions passed 96340 checks. The local native build is blocked by NeoForm being unable to resolve ProcessHandle.current().info().command() in the sandbox. Historical validation files are not evidence for this checkpoint.

## Milestones

- Finalize 2a acceptance: copied-world migration, upgrade behavior, integrations, survival progression and performance soak.
- 0.0.3a: finish machine-definition consolidation, targeted regression coverage, long-operation audit and diagnostics. Current definitions cover defaults only, not special-program dispatch or capacities.
- 0.0.4a: separate platform-specific code and produce tested 1.21.1 and 26.3 builds; mature Guide API co-loading and required pack adapters. Verify current official platform and dependency availability before choosing 26.3 coordinates; do not merely rename a 1.21.1 JAR.
- 0.0.5a: effective configuration UX, advancements/onboarding and remaining visual/audio polish; migration, multiplayer and performance acceptance on both supported platforms.
- 26.4 preparation means isolating version-specific APIs. It does not establish compatibility before an actual 26.4 build and test.

Each completed revision gets its own matching version tag and release assets. The included workflow currently validates 1.21.1 only; expand its matrix only after the actual 26.3 port builds and passes. Source-only checkpoints must remain clearly labelled development work.
