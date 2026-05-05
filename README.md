# Notable Players

A [RuneLite](https://runelite.net) plugin that highlights notable Old School RuneScape players when they appear near you in-game.

## What gets highlighted

- **Content Creators** and **Unique Accounts** — curated lists pulled from this repository. Submit additions via [the issue form](../../issues/new/choose).
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
### Content Creators

| Player | Description |
| --- | --- |
| **A Friend** | Largest OSRS YouTube channel |
| **Alfierules** | PK streamer |
| **Alkan** | Progress YouTuber/streamer |
| **B0aty** | Twitch streamer |
| **Bonesaw** | PK and progress YouTuber |
| **CampinOnline** | Clue scroll content creator |
| **Chris Archie** | ChrisArchieProds YouTuber |
| **Faux** | Progress streamer |
| **Framed** | PK YouTuber / speedrunner |
| **Graphist** | PK streamer |
| **Manked** | PK YouTuber; 2017 DMM champ |
| **Mmorpg** | PvM and progress streamer |
| **Mote Plox** | OSRS history YouTuber |
| **Mr Mammal** | Progress and guides YouTuber |
| **Rajj Patel** | Comedic streamer |
| **Rendimento** | First level-3 fire cape |
| **ReturnOfWilderness** | PK and gambling content |
| **Settled** | YouTube — Swampletics, etc. |
| **Sick Nerd** | Former rank 1 overall |
| **Skill Specs** | PK YouTuber/streamer |
| **Slayermusiq1** | Quest guides YouTuber |
| **Sparc Mac** | PK content creator |
| **Syratube** | Money-making YouTuber |
| **Torvesta** | PK and progress YouTuber |
| **Vio** | RuneScape documentary maker |
| **Woox** | First infernal cape; first solo ToB |
| **xzact** | Lowest-combat infernal cape (lvl 40) |

### Unique Accounts

| Player | Description |
| --- | --- |
| **10th Vongola** | First 99 Cooking |
| **Acid Soul** | First 200M Mining |
| **Alfie Shark** | First 99 Fishing |
| **Arthrima** | First 99 Mining |
| **Aspersion** | First 50+ in every skill |
| **Atrate** | First 99 in OSRS (Thieving) |
| **Bonno51** | First 99 Fletching |
| **Bradfordly** | First 200M Agility |
| **Calisme** | First 1M+ XP in every skill |
| **Clayton** | First 200M Magic |
| **Dearranged** | First 99 Firemaking |
| **Destiny** | First 200M Firemaking |
| **Du Old Pker** | First 200M Thieving |
| **Eetsk** | First 200M Farming |
| **Elder Jr** | First 99 Defence |
| **Elvis99** | First 99 Hunter |
| **Evil Oak** | First 200M Hunter |
| **Gentle Falls** | First 99 Herblore |
| **Gnome Daddy** | First 99 Runecrafting & Construction |
| **H O U S A N** | First 99 Hitpoints |
| **Hawklit3** | First 99 Attack |
| **Heur** | First 200M Attack |
| **Home Page** | First 200M Fishing & Defence |
| **Hyger** | First 99 Strength |
| **iDrizzay** | First 99 in all combat skills |
| **Itz Hickton** | First 200M Fletching |
| **Jebrim** | First maxed account |
| **KrimsonKueen** | First 200M Smithing |
| **Kusiak1** | First 99 Magic |
| **Lv 100 Hax** | First 99 Ranged |
| **Lynx Titan** | First 200M XP in all skills |
| **Malt Lickeys** | First 200M Slayer |
| **Martin 2007** | First 200M Strength |
| **Metal Shad0w** | First 200M Ranged |
| **Mini Finbarr** | First 99 Runecraft without runners |
| **mmiq** | First 99 Prayer |
| **NoToAfkPray** | First 200M Prayer |
| **o7 Botter** | First 99 Fishing while PKing in PvP |
| **Paulrat 3** | First 99 and 200M Crafting |
| **Pur** | First total 250, 500, 1000 |
| **Pureyoo** | First 99 Woodcutting |
| **Ra Ra** | First 99 Smithing |
| **Randalicious** | First 200M Construction |
| **Razzeh** | First 99 Slayer; first total 2000 |
| **Runes** | First 200M Runecraft |
| **Sathon** | First 200M Woodcutting |
| **Sc0ooby Doo** | First 85 Slayer (abyssal demons) |
| **Sjoerd nl** | First 99 Att/Def with 1 Strength |
| **Stupidman990** | First 1M XP in a skill (Fletching) |
| **suckitlosers** | First 200M XP (Cooking) |
| **Talk-to** | First 200M Herblore |
| **unik4kosova** | First total level 1500 |
| **Unohdettu2** | First 200M Hitpoints |
| **Vestfold** | First 1B total XP |
| **Word man** | First on OSRS hiscores |
| **Xav777** | First combat 126 |
| **Yalps** | First 80+ in every skill |
| **Yosumin** | First 99 Farming |
| **Zezima** | Iconic early high-level account |

### Jagex Mods

Any player whose name starts with `Mod ` is highlighted automatically.
<!-- END PLAYERS -->

## Building / running

This is a standard RuneLite external-plugin Gradle project. `./gradlew run` launches RuneLite with the plugin loaded.

## License

[BSD 2-Clause](LICENSE).
