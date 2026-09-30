# R8.1 acceptance checklist

1. Build with Java 21 and NeoForge 21.1.250 using `clean test build`.
2. With neither Patchouli nor a master reader installed, use `foundations_calculator:info_calculator`; it must land on **Welcome to Foundations Calculator** and link to **Getting Started: First 10 Minutes**.
3. Complete the quick-start navigation and verify the links to the Hand-Cranked Generator, Power Cube, Extraction Chamber, circuit chain, research and first workshop.
4. Install Patchouli for Minecraft 1.21.1 and confirm it loads `foundations_calculator:field_guide` without creating a second Calculator guide item. Confirm the Welcome and Getting Started entries render.
5. Remove Patchouli and confirm Calculator still starts and the standalone guide works.
6. Register the SDK example/future master host and confirm the exact `foundations_calculator:field_guide#foundations_calculator:getting_started/welcome` target opens there when accepted.
7. Make the host decline or remove it and confirm standalone fallback.
8. Confirm opening any reader does not modify research/mastery state.
9. Resource reload: edit a guide resource in a test resource pack, reload, and verify both the Foundations catalog and Patchouli content update according to their own reload rules.
10. Validate a copied world after restart and keep the previous working JAR until all checks pass.
