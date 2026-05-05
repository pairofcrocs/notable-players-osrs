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

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Notable Players",
	description = "Highlights notable players (streamers, Jagex Mods, unique accounts) and your own custom list",
	tags = {"highlight", "players", "streamers", "mods", "unique"}
)
public class NotablePlayersPlugin extends Plugin
{
	@Inject private OverlayManager overlayManager;
	@Inject private NotablePlayersOverlay overlay;
	@Inject private NotablePlayersMinimapOverlay minimapOverlay;
	@Inject private NotablePlayersInfoOverlay infoOverlay;
	@Inject private NotablePlayersData data;
	@Inject private NotablePlayersConfig config;
	@Inject private ClientThread clientThread;

	@Override
	protected void startUp()
	{
		overlay.rebuildLookup();
		overlayManager.add(overlay);
		overlayManager.add(minimapOverlay);
		overlayManager.add(infoOverlay);
		if (config.fetchRemoteList())
		{
			data.refresh(() -> clientThread.invoke(overlay::rebuildLookup));
		}
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		overlayManager.remove(minimapOverlay);
		overlayManager.remove(infoOverlay);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!NotablePlayersConfig.GROUP.equals(event.getGroup()))
		{
			return;
		}
		overlay.rebuildLookup();
		if ("fetchRemoteList".equals(event.getKey()) && config.fetchRemoteList())
		{
			data.refresh(() -> clientThread.invoke(overlay::rebuildLookup));
		}
	}

	@Provides
	NotablePlayersConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NotablePlayersConfig.class);
	}
}
