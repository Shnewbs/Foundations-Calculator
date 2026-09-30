# Foundations Calculator 0.0.1a.R9 — Field Guide finalization

R9 is a presentation/finalization revision for the existing Calculator Field Guide. It does **not** import the Practical Logistics display renderer work and does not add new gameplay systems.

## Native reader

The standalone reader now uses the Foundations guidebook family rather than the R8 browser-like layout:

- centered soft-grey leather book shell;
- warm parchment page block;
- visible center binding on wide layouts;
- left-page search/contents and right-page entry reading;
- external chapter tabs for all ten Calculator chapters;
- compact single-page Contents/Read mode for narrow windows;
- styled notes, headings, tables, live values, item references, recipe links and blueprint layers;
- saved-entry, history, refresh and Research controls integrated into the book;
- no player-facing raw `End of entry` IDs.

The reader still avoids the vanilla blur pass so the book, text and controls remain crisp.

## Field Guide item

`foundations_calculator:info_calculator` keeps the same registry ID, recipe and use behavior. Its rendered item is now a soft-grey leather book with graphite binding, parchment page edges, a restrained cyan Calculator emblem and a small cyan bookmark.

## Live values

Calculator server snapshots remain bounded and read-only. R9 publishes friendly labels such as `Capacity: 1,000,000 FE` and `Item automation: Yes` instead of exposing raw TOML keys in normal guide pages. Power diagnostics keep their detailed technical wording.

## Interoperability preserved

R9 keeps:

- Foundations Guide API 1.0.0;
- guide ID `foundations_calculator:field_guide`;
- landing entry `foundations_calculator:getting_started/welcome`;
- 108 authored entries and 10 chapters;
- generated Patchouli mirror without a duplicate book item;
- future master-guide host acceptance and standalone fallback;
- public recipe/live provider boundaries.

The master-guide mod itself is still a later standalone project.
