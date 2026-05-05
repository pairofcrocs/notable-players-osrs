/*
 * Copyright (c) 2026, pairofcrocs
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
		description = "The Streamers and Unique Accounts lists are bundled with the plugin. Jagex Mods are detected automatically by the 'Mod ' name prefix. Open a pull request against the plugin repository to add or remove a name.",
		position = 0,
		closedByDefault = false
	)
	String aboutSection = "about";

	@ConfigSection(name = "Streamers",       description = "Display options for streamers",       position = 1, closedByDefault = false) String streamersSection = "streamers";
	@ConfigSection(name = "Jagex Mods",      description = "Display options for Jagex Mods",      position = 2, closedByDefault = false) String modsSection      = "mods";
	@ConfigSection(name = "Unique Accounts", description = "Display options for unique accounts", position = 3, closedByDefault = false) String uniquesSection   = "uniques";
	@ConfigSection(name = "Custom",          description = "Your own list of names",              position = 4, closedByDefault = false) String customSection    = "custom";
	@ConfigSection(name = "Display",         description = "What to draw",                        position = 5, closedByDefault = false) String displaySection   = "display";

	@ConfigItem(
		keyName = "submitInfo",
		name = "How to submit",
		description = "To add or change a name, open an issue or PR against the plugin repository. Jagex Mods don't need to be submitted — they're matched automatically by name prefix.",
		section = aboutSection,
		position = 0
	)
	default String submitInfo() { return "See description — open an issue to add a name."; }

	@ConfigItem(
		keyName = "fetchRemoteList",
		name = "Fetch list from GitHub",
		description = "When on, the plugin downloads the latest curated list from the plugin's GitHub repository on startup. When off, only the list bundled with the plugin is used.",
		section = aboutSection,
		position = 1
	)
	default boolean fetchRemoteList() { return true; }

	@Alpha
	@ConfigItem(keyName = "streamerColor", name = "Color", description = "Color used for streamers", section = streamersSection, position = 0)
	default Color streamerColor() { return new Color(170, 0, 255); }

	@ConfigItem(keyName = "streamerLabel", name = "Label", description = "Text drawn above streamers", section = streamersSection, position = 1)
	default String streamerLabel() { return "Streamer"; }

	@Alpha
	@ConfigItem(keyName = "modColor", name = "Color", description = "Color used for Jagex Mods", section = modsSection, position = 0)
	default Color modColor() { return new Color(255, 215, 0); }

	@ConfigItem(keyName = "modLabel", name = "Label", description = "Text drawn above Jagex Mods", section = modsSection, position = 1)
	default String modLabel() { return "Jmod"; }

	@Alpha
	@ConfigItem(keyName = "uniqueColor", name = "Color", description = "Color used for unique accounts", section = uniquesSection, position = 0)
	default Color uniqueColor() { return new Color(0, 200, 255); }

	@ConfigItem(keyName = "uniqueLabel", name = "Label", description = "Text drawn above unique accounts", section = uniquesSection, position = 1)
	default String uniqueLabel() { return "Unique"; }

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

	@ConfigItem(keyName = "drawHull",      name = "Outline player",      description = "Draw an outline around the player's model",                          section = displaySection, position = 0)
	default boolean drawHull() { return true; }

	@ConfigItem(keyName = "drawTile",      name = "Highlight tile",      description = "Outline the tile beneath the player",                                section = displaySection, position = 1)
	default boolean drawTile() { return false; }

	@ConfigItem(keyName = "drawLabel",     name = "Show label",          description = "Draw the category label above the player's head",                    section = displaySection, position = 2)
	default boolean drawLabel() { return true; }

	@ConfigItem(keyName = "drawMinimap",   name = "Highlight minimap",   description = "Draw the player's name on the minimap in their highlight color",     section = displaySection, position = 3)
	default boolean drawMinimap() { return true; }

	@ConfigItem(keyName = "drawInfoPanel", name = "Show info panel",     description = "Show a panel listing nearby notable players and why they're notable", section = displaySection, position = 4)
	default boolean drawInfoPanel() { return true; }

	@ConfigItem(keyName = "showCreator",   name = "Show plugin creator", description = "I added myself to the plugin — if you see me in game, come say hi!", section = displaySection, position = 5)
	default boolean showCreator() { return true; }
}
