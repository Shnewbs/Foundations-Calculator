# Foundations Calculator 0.0.1a.R8.1 — Welcome, Getting Started, Patchouli and Master Guide Interop

## Purpose
R8.1 makes the Field Guide easier for a new player to enter and makes the same documentation discoverable by multiple readers without creating multiple required book items.

## Welcome and Getting Started
The guide landing entry is `foundations_calculator:getting_started/welcome` and is titled **Welcome to Foundations Calculator**. It explains what the Field Guide covers, how to navigate it, the difference between documentation and progression, and how alternate readers work.

A dedicated `foundations_calculator:getting_started/quickstart` entry is titled **Getting Started: First 10 Minutes**. It walks through the first Calculator, early power, Power Cube, Extraction Chamber, dirty/damaged/clean circuit order, Research Chamber use, and the point where automation should be introduced.

`getting_started/first_workshop` remains the deeper follow-on workshop page instead of duplicating the quick start.

## Standalone reader
The existing `foundations_calculator:info_calculator` remains the only Calculator guide item. Its normal standalone reader remains a complete fallback.

## Foundations master-reader contract
The authoritative structured guide remains:

- guide id: `foundations_calculator:field_guide`
- API artifact: `com.foundations:foundations-guide-api:1.0.0`
- schema: 1
- landing target: `foundations_calculator:getting_started/welcome`

A future master guide registers a `GuideApi.Host`. If that host accepts the book, supports its capabilities, and successfully opens the requested target, Calculator can use that reader. Otherwise Calculator falls back to its own Field Guide screen.

No master-guide mod is bundled with R8.1.

## Patchouli bridge
R8.1 generates a Patchouli resource mirror from the authoritative Foundations guide content.

Patchouli book id:

`foundations_calculator:field_guide`

The book definition is stored under the standard `data/foundations_calculator/patchouli_books/field_guide/book.json` location and the content under `assets/foundations_calculator/patchouli_books/field_guide/en_us/...`.

The bridge uses:

- `use_resource_pack: true`
- `dont_generate_book: true`
- `custom_book_item: foundations_calculator:info_calculator`

This intentionally prevents Patchouli from adding a second Calculator guide-book item. Patchouli is optional and is not bundled or required by Calculator.

Patchouli pages mirror all static chapters/entries. Foundations-only live server values, layered diagrams, and live recipe-provider panels degrade to explanatory text telling the reader to use the native/master Foundations reader for those dynamic views.

## Single-source authoring
`tools/author_field_guide.py` remains the authoritative content generator. It invokes `tools/generate_patchouli_bridge.py` after writing the Foundations guide so the Patchouli mirror cannot silently drift away from the source guide.

`assets/foundations_calculator/foundations_guides/field_guide/interop.json` publishes reader metadata for tooling and future integrations.

## Compatibility boundaries
- Installing Patchouli is optional.
- Removing Patchouli does not remove the Calculator guide.
- Installing a future master reader is optional.
- Reading any representation of the guide does not grant research or bypass server progression.
- A Patchouli mirror cannot expose Calculator's live server snapshots without additional integration; it remains a static documentation mirror.
- Third-party books are not automatically imported into the future master guide. They need the Foundations Guide API or an explicit adapter.
