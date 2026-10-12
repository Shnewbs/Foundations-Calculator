# CurseForge distribution

Project ID: 1734096. Repository secret: CURSEFORGE_API_TOKEN.

GitHub remains the source of release binaries and validation evidence. After the validated GitHub publication workflow succeeds, Publish Calculator to CurseForge downloads that exact audited binary and submits it to CurseForge. The workflow can also be dispatched for an existing audited release. No rebuild or candidate source ZIP is uploaded.

Candidate mapping: 0.0.xa / 0.1a and revisions → alpha; 0.1b and beta/rc versions → beta; plain x.y.z → release. Unknown names fail closed. New platform JARs need their own verified platform metadata before enabling uploads; 1.21.1 artifacts are never labelled 26.3/26.4.

Successful uploads attach curseforge-upload.json to the GitHub release to prevent automatic duplicates. If CurseForge accepts the file but attaching the receipt fails, recover the retained receipt artifact and attach it before retrying. Do not blindly rerun after an ambiguous network failure; check the portal for the accepted file first.

DESCRIPTION.md is the maintained page copy. The documented upload API supports file/changelog metadata, not project page descriptions, so page copy is applied through the author portal. Description maintenance is authorized; no undocumented description endpoint is used.

Port work continues on port/26.3 with Java 25 / NeoForge 26.3.0.52-beta. It already includes identifier/client API groundwork, default-valued NBT migration, UUID preservation and transactional energy tests, but full compilation remains blocked. 26.4 preparation consists of isolating these platform changes; compatibility is not yet verified.

API reference: https://support.curseforge.com/support/solutions/articles/9000197321-curseforge-upload-api
