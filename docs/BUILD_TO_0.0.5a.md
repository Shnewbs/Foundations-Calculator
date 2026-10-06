# Build toward 0.0.5a

Keep Minecraft 1.21.1 supported, port to 26.3, prepare for 26.4, and publish each completed revision through GitHub Releases.

The 0.0.3a-dev.1 release passed native compilation, JUnit, 124 standalone GameTests, 124 integration GameTests, asset/progression checks and the release audit. Its binary and source JARs are on GitHub Releases. The Guide API development classpath, FE registration priority and Patchouli audit path were corrected after actual failures.

The 0.0.4a revision adds native long transfer scaling, including a true zero multiplier, diagnostics and three runtime tests. The 0.0.5a revision adds operator configuration inspection, five informational advancements, guide instructions and two runtime tests. Each revision must pass its matching release audit before publishing.

These are 1.21.1 alpha revisions. The broader roadmap is not complete: the 26.3 API migration, platform isolation, remaining visual/audio polish, copied-world migration, multiplayer/client checks and performance soak remain outstanding. Version 26.3 requires Java 25 and a dedicated NeoForge build; the 1.21.1 binary cannot be reused. Version 26.4 compatibility requires an actual future build and acceptance run.
