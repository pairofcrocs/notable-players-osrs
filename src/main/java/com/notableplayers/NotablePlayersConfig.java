package com.notableplayers;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(NotablePlayersConfig.GROUP)
public interface NotablePlayersConfig extends Config
{
    String GROUP = "notableplayers";

    @ConfigSection(
        name = "About",
        description = "The Streamers, Jagex Mods, and Unique Accounts lists are bundled with the plugin. To get a player added (or removed), open a pull request against the plugin repository — see the 'How to submit' field below.",
        position = 0,
        closedByDefault = false
    )
    String aboutSection = "about";

    @ConfigSection(name = "Streamers",       description = "Display options for streamers",       position = 1, closedByDefault = false) String streamersSection = "streamers";
    @ConfigSection(name = "Jagex Mods",      description = "Display options for Jagex Mods",      position = 2, closedByDefault = false) String modsSection      = "mods";
    @ConfigSection(name = "Unique Accounts", description = "Display options for unique accounts", position = 3, closedByDefault = false) String uniquesSection   = "uniques";
    @ConfigSection(name = "Custom",          description = "Your own list of names",              position = 4, closedByDefault = false) String customSection    = "custom";
    @ConfigSection(name = "Display",         description = "What to draw",                        position = 5, closedByDefault = false) String displaySection   = "display";

    // ---------- About ----------

    @ConfigItem(
        keyName = "submitInfo",
        name = "How to submit",
        description = "To add or change a name, open a PR against the plugin repository with your edit to notable_players.json. Include a short reason describing why the player is notable.",
        section = aboutSection,
        position = 0
    )
    default String submitInfo() { return "See description — open a PR to add a name."; }

    // ---------- Streamers ----------

    @Alpha
    @ConfigItem(keyName = "streamerColor", name = "Color", description = "Color used for streamers", section = streamersSection, position = 0)
    default Color streamerColor() { return new Color(170, 0, 255); }

    @ConfigItem(keyName = "streamerLabel", name = "Label", description = "Text drawn above streamers", section = streamersSection, position = 1)
    default String streamerLabel() { return "Streamer"; }

    // ---------- Jagex Mods ----------

    @Alpha
    @ConfigItem(keyName = "modColor", name = "Color", description = "Color used for Jagex Mods", section = modsSection, position = 0)
    default Color modColor() { return new Color(255, 215, 0); }

    @ConfigItem(keyName = "modLabel", name = "Label", description = "Text drawn above Jagex Mods", section = modsSection, position = 1)
    default String modLabel() { return "Jmod"; }

    // ---------- Unique accounts ----------

    @Alpha
    @ConfigItem(keyName = "uniqueColor", name = "Color", description = "Color used for unique accounts", section = uniquesSection, position = 0)
    default Color uniqueColor() { return new Color(0, 200, 255); }

    @ConfigItem(keyName = "uniqueLabel", name = "Label", description = "Text drawn above unique accounts", section = uniquesSection, position = 1)
    default String uniqueLabel() { return "Unique"; }

    // ---------- Custom (user-managed) ----------

    @ConfigItem(
        keyName = "custom",
        name = "Names",
        description = "One per line. Format: 'Name:Description'. The description shows in the info panel. The description is optional — bare names work too.",
        section = customSection,
        position = 0
    )
    default String custom() { return ""; }

    @Alpha
    @ConfigItem(keyName = "customColor", name = "Color", description = "Color used for custom highlights", section = customSection, position = 1)
    default Color customColor() { return new Color(0, 255, 0); }

    @ConfigItem(keyName = "customLabel", name = "Label", description = "Text drawn above custom highlights", section = customSection, position = 2)
    default String customLabel() { return "Notable"; }

    // ---------- Display ----------

    @ConfigItem(keyName = "drawHull",      name = "Outline player",    description = "Draw an outline around the player's model",                          section = displaySection, position = 0)
    default boolean drawHull() { return true; }

    @ConfigItem(keyName = "drawTile",      name = "Highlight tile",    description = "Outline the tile beneath the player",                                section = displaySection, position = 1)
    default boolean drawTile() { return false; }

    @ConfigItem(keyName = "drawLabel",     name = "Show label",        description = "Draw the category label above the player's head",                    section = displaySection, position = 2)
    default boolean drawLabel() { return true; }

    @ConfigItem(keyName = "drawMinimap",   name = "Highlight minimap", description = "Draw the player's name on the minimap in their highlight color",     section = displaySection, position = 3)
    default boolean drawMinimap() { return true; }

    @ConfigItem(keyName = "drawInfoPanel", name = "Show info panel",   description = "Show a panel listing nearby notable players and why they're notable", section = displaySection, position = 4)
    default boolean drawInfoPanel() { return true; }

    @ConfigItem(keyName = "showCreator",   name = "Show plugin creator", description = "Include the plugin creator in highlights and the info panel", section = displaySection, position = 5)
    default boolean showCreator() { return true; }
}
