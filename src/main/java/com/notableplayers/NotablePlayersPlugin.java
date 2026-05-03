package com.notableplayers;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
    name = "Notable Players",
    description = "Highlights manually-listed players (streamers, Jagex mods, unique accounts, etc.)",
    tags = {"highlight", "players", "streamers", "mods", "unique"}
)
public class NotablePlayersPlugin extends Plugin
{
    @Inject private OverlayManager overlayManager;
    @Inject private NotablePlayersOverlay overlay;
    @Inject private NotablePlayersMinimapOverlay minimapOverlay;
    @Inject private NotablePlayersInfoOverlay infoOverlay;

    @Override
    protected void startUp()
    {
        overlay.rebuildLookup();
        overlayManager.add(overlay);
        overlayManager.add(minimapOverlay);
        overlayManager.add(infoOverlay);
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
        if (NotablePlayersConfig.GROUP.equals(event.getGroup()))
        {
            overlay.rebuildLookup();
        }
    }

    @Provides
    NotablePlayersConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(NotablePlayersConfig.class);
    }
}
