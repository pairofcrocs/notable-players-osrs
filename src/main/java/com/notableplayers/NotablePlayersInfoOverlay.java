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
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

@Singleton
public class NotablePlayersInfoOverlay extends OverlayPanel
{
	private final Client client;
	private final NotablePlayersConfig config;
	private final NotablePlayersOverlay sceneOverlay;

	@Inject
	NotablePlayersInfoOverlay(Client client, NotablePlayersConfig config, NotablePlayersOverlay sceneOverlay)
	{
		this.client = client;
		this.config = config;
		this.sceneOverlay = sceneOverlay;
		setPosition(OverlayPosition.TOP_RIGHT);
		setPriority(OverlayPriority.LOW);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.drawInfoPanel())
		{
			return null;
		}

		List<Match> matches = new ArrayList<>();
		for (Player p : client.getPlayers())
		{
			Highlight h = sceneOverlay.matchFor(p);
			if (h != null)
			{
				matches.add(new Match(p.getName(), h));
			}
		}

		if (matches.isEmpty())
		{
			return null;
		}

		matches.sort(Comparator.comparing(m -> m.name.toLowerCase()));

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Notable Players")
			.build());

		for (Match m : matches)
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left(m.name)
				.leftColor(m.highlight.color)
				.build());

			if (m.highlight.reason != null && !m.highlight.reason.isEmpty())
			{
				panelComponent.getChildren().add(LineComponent.builder()
					.left(m.highlight.reason)
					.leftColor(Color.LIGHT_GRAY)
					.build());
			}
		}

		return super.render(graphics);
	}

	private static final class Match
	{
		final String name;
		final Highlight highlight;

		Match(String name, Highlight highlight)
		{
			this.name = name;
			this.highlight = highlight;
		}
	}
}
