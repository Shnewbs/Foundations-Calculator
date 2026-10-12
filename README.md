# Foundations Calculator 0.1.0beta — development candidate

Minecraft 1.21.1 · NeoForge 21.1.250 · Java 21.

This candidate adds long-valued processing costs, portable calculations and native item charging/discharging to the cumulative 0.0.5a.R2 line. Machine screens, Jade and recipe/guide displays report the full effective cost. The standard NeoForge FE interface remains bounded by its API contract. Client/server gameplay protocol is now 7; install matching versions on both sides.

Build with `BUILD.bat` or `bash gradlew clean test build`. Validate with `python tools/validate_release.py`; the command regenerates maintained configuration/guide resources, builds binary/source JARs, runs JUnit plus standalone and integration GameTests, and audits the exact source and artifacts. Local validation works independently of GitHub Actions.

Native candidate verification: **31 JUnit tests, 142 standalone GameTests and 142 integration GameTests excluding GTCEu pass**. The combined integration JVM was killed with exit 137. See [validation evidence](docs/0.1.0beta_VALIDATION.md).

The last published release is **0.0.5a.R2**. The 0.1.0beta release gates are tracked in [the beta checklist](docs/0.1.0beta_ACCEPTANCE.md). A version string alone does not certify those gates. Separate 26.1.2 and 26.3 branches remain under migration; 26.4 compatibility is unverified.
