package com.notableplayers;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class NotablePlayersPluginTest
{
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(NotablePlayersPlugin.class);
        RuneLite.main(args);
    }
}
