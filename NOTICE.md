# Attribution

Foundations Calculator is a Minecraft 1.21.1 adaptation of Calculator and the portions of Sonar Core needed by Calculator. It is distributed as one mod, `foundations_calculator`. This is an independent Foundations adaptation; original authorship and artwork remain credited below.

- Calculator by Ollie Lansdell / SonarSonic: https://github.com/SonarSonic/Calculator
  - Branch: `1.12.2`
  - Commit: `c796f1dc24e937f95cb5d8573f485b66e87164b4`
- Sonar Core by Ollie Lansdell / SonarSonic: https://github.com/SonarSonic/Sonar-Core
  - Branch: `1.12.2`
  - Commit: `f5b64e033e2c07af55c2d5f474052fb83fba5ca1`

Both upstream repositories use the MIT License, copyright (c) 2018 Ollie Lansdell. Their notices are preserved in LICENSE and licenses/. Recipe declarations, material names, textures, and converted models derive from those projects. Original texture bytes are retained under `textures/legacy_calculator` and `textures/legacy_sonarcore` inside the Foundations namespace. JSON resource paths and state definitions have been adapted for 1.21.1.

The Gradle wrapper comes from the NeoForge 1.21.1 ModDevGradle MDK template, https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle. Gradle wrapper scripts retain their Apache 2.0 notices; a copy of that license is in licenses/.

NeoForge, Minecraft, KubeJS, Rhino, and JUnit are resolved development/runtime dependencies. Their code and game assets are not bundled into the Foundations release JAR. KubeJS is optional. No original Sonar Core JAR is embedded or required.
