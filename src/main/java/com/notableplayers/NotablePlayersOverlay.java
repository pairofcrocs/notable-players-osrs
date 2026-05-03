package com.notableplayers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.util.Text;

@Singleton
public class NotablePlayersOverlay extends Overlay
{
    private static final Stroke OUTLINE_STROKE = new BasicStroke(2f);

    private final Client client;
    private final NotablePlayersConfig config;
    private final NotablePlayersData data;
    private final Map<String, Highlight> lookup = new HashMap<>();

    @Inject
    NotablePlayersOverlay(Client client, NotablePlayersConfig config, NotablePlayersData data)
    {
        this.client = client;
        this.config = config;
        this.data = data;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.MED);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    private static final Color CREATOR_COLOR = new Color(255, 130, 0);
    private static final String CREATOR_LABEL = "Plugin Creator";

    void rebuildLookup()
    {
        lookup.clear();

        NotablePlayersData.Bundle bundle = data.load();
        if (config.showCreator())
        {
            addBundled(bundle.creators, CREATOR_COLOR, CREATOR_LABEL);
        }
        addBundled(bundle.streamers, config.streamerColor(), config.streamerLabel());
        addBundled(bundle.mods,      config.modColor(),      config.modLabel());
        addBundled(bundle.uniques,   config.uniqueColor(),   config.uniqueLabel());

        addCustom(config.custom(), config.customColor(), config.customLabel());
    }

    private void addBundled(java.util.List<NotablePlayersData.Entry> entries, Color color, String label)
    {
        if (entries == null) return;
        for (NotablePlayersData.Entry e : entries)
        {
            if (e == null || e.name == null) continue;
            String key = Text.standardize(e.name.trim());
            if (key.isEmpty()) continue;
            lookup.put(key, new Highlight(color, label, e.reason == null ? "" : e.reason));
        }
    }

    private void addCustom(String raw, Color color, String label)
    {
        if (raw == null || raw.isEmpty()) return;
        for (String line : raw.split("\\R"))
        {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            String name;
            String reason;
            int sep = trimmed.indexOf(':');
            if (sep >= 0)
            {
                name = trimmed.substring(0, sep).trim();
                reason = trimmed.substring(sep + 1).trim();
            }
            else
            {
                name = trimmed;
                reason = "";
            }

            String key = Text.standardize(name);
            if (key.isEmpty()) continue;
            lookup.put(key, new Highlight(color, label, reason));
        }
    }

    Highlight matchFor(Player p)
    {
        if (p == null || p.getName() == null || lookup.isEmpty() || p == client.getLocalPlayer())
        {
            return null;
        }
        return lookup.get(Text.standardize(p.getName()));
    }

    boolean isEmpty()
    {
        return lookup.isEmpty();
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (lookup.isEmpty())
        {
            return null;
        }

        for (Player p : client.getPlayers())
        {
            Highlight h = matchFor(p);
            if (h == null)
            {
                continue;
            }
            renderPlayer(graphics, p, h);
        }
        return null;
    }

    private void renderPlayer(Graphics2D graphics, Player p, Highlight h)
    {
        if (config.drawHull())
        {
            Shape hull = p.getConvexHull();
            if (hull != null)
            {
                Stroke prev = graphics.getStroke();
                graphics.setStroke(OUTLINE_STROKE);
                graphics.setColor(h.color);
                graphics.draw(hull);
                graphics.setStroke(prev);
            }
        }

        if (config.drawTile())
        {
            LocalPoint lp = p.getLocalLocation();
            if (lp != null)
            {
                Polygon poly = Perspective.getCanvasTilePoly(client, lp);
                if (poly != null)
                {
                    OverlayUtil.renderPolygon(graphics, poly, h.color);
                }
            }
        }

        if (config.drawLabel() && h.label != null && !h.label.isEmpty())
        {
            Point textLoc = p.getCanvasTextLocation(graphics, h.label, p.getLogicalHeight() + 40);
            if (textLoc != null)
            {
                OverlayUtil.renderTextLocation(graphics, textLoc, h.label, h.color);
            }
        }
    }
}
