# Foundations Calculator R7-HF1 — test-classpath build repair

## What the supplied Windows log proves

The R5/R6-to-R7 source updater applied successfully. `compileJava`, `processResources`,
`jar`, and `sourcesJar` then completed on the user's computer. `compileTestJava`
failed with nine diagnostics beginning with the missing package
`net.neoforged.neoforge.energy`, referenced by `PowerCoreAssertions`.
The later `StoredEnergy`/`IEnergyStorage` type errors follow that missing test dependency.
JUnit did not run in that attempt; JAR creation by itself was not successful build acceptance.
The folder's old R6 name is not the source version: the updater already changed it to R7.

## Correction

The R7 build script added `src/testSupport/java` to the unit-test source set but never
attached ModDevGradle's NeoForge/Minecraft dependencies to that source set. Main sources
already had those dependencies. HF1 adds this public ModDevGradle API call:

```groovy
neoForge.addModdingDependenciesTo(sourceSets.test)
```

This connects the platform to BOTH the test compilation and runtime classpaths. It
reuses the existing pinned NeoForge 21.1.250 / ModDevGradle 2.0.147 configuration; it
adds no optional mod, no separate Sonar Core, no offline stand-in classes, and no
full-game bootstrap to these arithmetic/policy JUnit tests.

Official API reference: https://github.com/NeoForged/ModDevGradle#isolated-source-sets
Official implementation: https://github.com/NeoForged/ModDevGradle/blob/main/src/main/java/net/neoforged/moddevgradle/dsl/ModDevExtension.java

`EnergyApiClasspathTest` is added as a regression. It checks that production
`StoredEnergy` implements the resolved FE interface, that the interface is loaded
from a JAR rather than an offline fixture directory, and that a simulated receive
is read-only followed by a real receive/extract through the interface.
This test requires the native Gradle environment; it has NOT run in the packaging environment.

## Deliberately unchanged

- `mod_version` remains `0.0.1a.R7`: HF1 is a build/test hotfix, not a gameplay revision.
- Expected local output remains `build/libs/FoundationsCalculator-0.0.1a.R7.jar`.
- Every production Java source and main resource is byte-identical to the delivered R7 source.
- Energy conversion settings, R7 protection, automation, performance changes, recipes,
  models, and textures are unchanged.
- All previous unit tests and 102 declared GameTests are retained. Nothing is skipped.
- The deprecation and optional annotation warnings in the supplied log are not fixed
  or suppressed by this targeted patch; they were not the reported compilation failure.

## Apply on Windows

1. Extract the entire `FoundationsCalculator-R7-HF1-BUILD-FIX.zip`.
2. Run `APPLY_R7_HF1.bat` from the extracted folder. It can run from anywhere.
3. Select the SAME source root that the previous updater changed: the directory
   containing `gradlew.bat`, `build.gradle`, and `gradle.properties`. It may still have
   R6 in its parent folder name. Do not roll back R7 first; do not select `mods`.
4. Answer Y to run `clean test build`.

The repair verifies its payload and every affected original file before changing
anything. Local conflicting edits or a partial hotfix are refused. It backs up
only the affected files beneath `.foundations_update_backups/Calculator_R7_HF1_*`.
`ROLLBACK_R7_HF1.bat` restores the pre-hotfix R7 source using that hotfix's receipt;
it does not undo R7 or replace installed JARs. Existing earlier backups are preserved.
A repeated run recognizes the exact applied hotfix and offers a rebuild without
rewriting the source. Noninteractive/verify-only options are available through
`Apply-Hotfix.ps1`: `-ProjectDirectory`, `-NoBuild`, `-NonInteractive`, `-VerifyOnly`.

Build acceptance requires Gradle exit code zero, at least eight JUnit tests with
zero failures/errors/skips, all four required unit-test suites, an R7 JAR, and no
packaged unit-test/GameTest/platform fixture classes. Native stderr is retained
in the build log without PowerShell's extra RemoteException formatting.

The updater DOES NOT install a mod or modify saves/configuration. A failed native
build leaves the repaired source and backup in place, reports failure, and installs
nothing. Keep the working installed JAR until native build and copied-world checks pass.

## Validation performed for this hotfix

Fresh isolated Java 21 runs: 96,300 energy-core checks, 16 power-policy checks,
22,343 R7 core checks, and 124 world-action-policy checks passed. These use the
existing explicitly test-only offline fixtures, NOT native NeoForge.
Syntax-only parsing passed 120 Java files; that is not dependency/type validation.

A fresh `gradlew clean test build --console=plain` attempt stopped in the wrapper
with `UnknownHostException: services.gradle.org`. No native Gradle configuration,
HF1 JUnit test, GameTest, client, or Windows PowerShell execution was possible here.
No precompiled JAR is included. Saved R5/R7 results are historical, not HF1 passes.

The package also includes filesystem-model checks for payload hashes, apply,
idempotency, rollback, and rejection of corrupted or conflicting files. These
exercise a Python model of the updater contract; they are NOT PowerShell execution.

After a successful local build, `VALIDATE_R7.bat` and
`VALIDATE_R7.bat integrations` remain the separate runtime acceptance steps.
