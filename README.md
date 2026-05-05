# Notable Players

A [RuneLite](https://runelite.net) plugin that highlights notable Old School RuneScape players when they appear near you in-game.

## What gets highlighted

- **Streamers** and **Unique Accounts** — curated lists pulled from this repository. Submit additions via [the issue form](../../issues/new/choose).
- **Jagex Mods** — matched automatically by the reserved `Mod ` name prefix; no submissions needed.
- **Custom** — your own list, managed in the plugin's config (`Name:Description` per line).

Each category has its own configurable color and label. Display options include model outline, tile, floating label, minimap text, and a top-right info panel that lists nearby matches with the reason they're notable. Your own character is never highlighted.

## How the list is loaded

The plugin ships with a copy of [`notable_players.json`](src/main/resources/com/notableplayers/notable_players.json) embedded in the JAR. **On every plugin startup it also fetches the latest copy of that file directly from this repository** at `raw.githubusercontent.com/pairofcrocs/notable-players-osrs/main/...`, so any merged submission goes live the next time the plugin starts — no plugin update required.

If the network request fails for any reason (no internet, GitHub unreachable, malformed payload), the bundled copy is used instead. You can disable the network fetch entirely with the **Fetch list from GitHub** toggle in the plugin's config; with it off, only the bundled list is consulted.

## Submitting a player

Open an issue using the [**Add a notable player**](../../issues/new/choose) form. You'll be asked for the username, category, a short description, and optional proof links.

Jagex Mods do not need to be submitted.

## Bundled players

The list below is generated automatically from [`notable_players.json`](src/main/resources/com/notableplayers/notable_players.json). Don't edit this section by hand — edit the JSON and the next push will regenerate it.

<!-- BEGIN PLAYERS -->
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

This is a standard RuneLite external-plugin Gradle project. `./gradlew run` launches RuneLite with the plugin loaded.

## License

[BSD 2-Clause](LICENSE).
