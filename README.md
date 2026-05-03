# Notable Players

A [RuneLite](https://runelite.net) plugin that highlights notable Old School RuneScape players — streamers, Jagex Mods, unique accounts, and your own custom additions — when they appear near you in-game.

## Features

- Outline, tile, label, and minimap highlights per category
- Floating info panel listing nearby notable players and why they're notable
- Bundled, curated list of streamers / Jagex Mods / unique accounts
- A Custom list you manage yourself in the plugin config (`Name:Description` per line)
- Per-category color and label overrides

## Submitting a player

Open an issue using the [**Add a notable player**](../../issues/new/choose) form. You'll be asked for the username, category, a short description, and optional proof links.

For Jagex Mods, **omit the `Mod ` prefix** — the plugin adds it automatically. Submit `Ash`, not `Mod Ash`.

## Bundled players

The list below is generated automatically from [`notable_players.json`](src/main/resources/com/notableplayers/notable_players.json). Don't edit this section by hand — edit the JSON and the next push will regenerate it.

<!-- BEGIN PLAYERS -->
### Plugin Creator

- **Two Crocs** — Notable Players plugin creator

### Streamers

- **B0aty** — Twitch streamer
- **Framed** — Speedrunner / streamer
- **Settled** — YouTube — Swampletics, etc.

### Jagex Mods

- **Mod Ash** — Jagex Mod — game content
- **Mod Kieren** — Jagex Mod

### Unique Accounts

- **Lynx Titan** — First account to 200M XP in all skills
- **Zezima** — Iconic early high-level account
<!-- END PLAYERS -->

## Building / running the plugin

This is a standard RuneLite external-plugin Gradle project.

- `./gradlew run` launches RuneLite with the plugin loaded (uses the bundled test main).
- `./run-jagex.sh` launches it with credentials borrowed from a currently-running official Jagex Launcher session (macOS).
