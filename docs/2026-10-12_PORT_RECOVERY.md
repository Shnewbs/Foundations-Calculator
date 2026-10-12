# Platform migration recovery checkpoint

The local workspace cleared while a CurseForge sign-in request was waiting. The previously pushed beta candidate remains intact. This checkpoint reconstructs registry-aware item codecs, crafting remainders, fuel lookups and ValueInput/ValueOutput machine persistence from that work. Save keys and separate bulk counts are retained.

The migration script is idempotent. Full compiler validation is pending the attached native 26.1.2 workflow; these source changes are not a playable release. Historical local compiler reductions before the reset must not be treated as validation of this reconstructed tree.

The 1.21.1 GitHub validation run 38254820229 passed 31 unit tests and both 142-test runtime suites, including the combined integration pack. Its beta acceptance remains incomplete. CurseForge uploads use the repository CURSEFORGE_API_TOKEN secret through the publisher workflow; no secret is extracted or committed.
