# Notable Players

A [RuneLite](https://runelite.net) plugin that highlights notable Old School RuneScape players when they appear near you in-game.

## What gets highlighted

- **Plugin Creator** — hardcoded; toggle off with the *Show plugin creator* setting.
- **Streamers** and **Unique Accounts** — bundled, curated lists. Submit additions via [the issue form](../../issues/new/choose).
- **Jagex Mods** — matched automatically by the reserved `Mod ` name prefix; no submissions needed.
- **Custom** — your own list, managed in the plugin's config (`Name:Description` per line).

Each category has its own configurable color and label. Display options include model outline, tile, floating label, minimap text, and a top-right info panel that lists nearby matches with the reason they're notable. Your own character is never highlighted.

## Submitting a player

Open an issue using the [**Add a notable player**](../../issues/new/choose) form. You'll be asked for the username, category, a short description, and optional proof links.

Jagex Mods do not need to be submitted.

## Bundled players

The list below is generated automatically from [`notable_players.json`](src/main/resources/com/notableplayers/notable_players.json). Don't edit this section by hand — edit the JSON and the next push will regenerate it.

<!-- BEGIN PLAYERS -->
### Plugin Creator

| Player | Description |
| --- | --- |
| **Two Crocs** | Notable Players plugin creator |

### Streamers

| Player | Description |
| --- | --- |
| **B0aty** | Twitch streamer |
| **Framed** | Speedrunner / streamer |
| **Settled** | YouTube — Swampletics, etc. |

### Unique Accounts

| Player | Description |
| --- | --- |
| **Lynx Titan** | First account to 200M XP in all skills |
| **Zezima** | Iconic early high-level account |

### Jagex Mods

Any player whose name starts with `Mod ` is highlighted automatically.
<!-- END PLAYERS -->

## Building / running

This is a standard RuneLite external-plugin Gradle project.

- `./gradlew run` launches RuneLite with the plugin loaded.
- `./run-jagex.sh` (macOS) launches it with credentials borrowed from a currently-running official Jagex Launcher session.

## License

[BSD 2-Clause](LICENSE).
