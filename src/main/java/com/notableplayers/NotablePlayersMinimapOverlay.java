package com.notableplayers;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

@Singleton
public class NotablePlayersMinimapOverlay extends Overlay
{
    private final Client client;
    private final NotablePlayersConfig config;
    private final NotablePlayersOverlay sceneOverlay;

    @Inject
    NotablePlayersMinimapOverlay(Client client, NotablePlayersConfig config, NotablePlayersOverlay sceneOverlay)
    {
        this.client = client;
        this.config = config;
        this.sceneOverlay = sceneOverlay;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.MED);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!config.drawMinimap() || sceneOverlay.isEmpty())
        {
            return null;
        }

        for (Player p : client.getPlayers())
        {
            Highlight h = sceneOverlay.matchFor(p);
            if (h == null)
            {
                continue;
            }
            Point mini = p.getMinimapLocation();
            if (mini != null)
            {
                OverlayUtil.renderTextLocation(graphics, mini, p.getName(), h.color);
            }
        }
        return null;
    }
}
