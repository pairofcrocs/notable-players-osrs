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
        if (!config.drawInfoPanel() || sceneOverlay.isEmpty())
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
