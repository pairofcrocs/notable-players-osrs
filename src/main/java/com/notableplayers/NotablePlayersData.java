package com.notableplayers;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
class NotablePlayersData
{
    static final class Entry
    {
        @SerializedName("name")   String name;
        @SerializedName("reason") String reason;
    }

    static final class Bundle
    {
        List<Entry> creators;
        List<Entry> streamers;
        List<Entry> mods;
        List<Entry> uniques;
    }

    private static final String RESOURCE = "/com/notableplayers/notable_players.json";

    private final Gson gson;
    private Bundle bundle;

    @Inject
    NotablePlayersData(Gson gson)
    {
        this.gson = gson;
    }

    Bundle load()
    {
        if (bundle != null)
        {
            return bundle;
        }
        try (InputStream in = NotablePlayersData.class.getResourceAsStream(RESOURCE))
        {
            if (in == null)
            {
                log.warn("Bundled notable player list not found at {}", RESOURCE);
                bundle = empty();
            }
            else
            {
                bundle = gson.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), Bundle.class);
                if (bundle == null)
                {
                    bundle = empty();
                }
            }
        }
        catch (Exception e)
        {
            log.warn("Failed to load bundled notable player list", e);
            bundle = empty();
        }
        if (bundle.creators == null)  bundle.creators = Collections.emptyList();
        if (bundle.streamers == null) bundle.streamers = Collections.emptyList();
        if (bundle.mods == null)      bundle.mods = Collections.emptyList();
        if (bundle.uniques == null)   bundle.uniques = Collections.emptyList();
        return bundle;
    }

    private static Bundle empty()
    {
        Bundle b = new Bundle();
        b.creators = Collections.emptyList();
        b.streamers = Collections.emptyList();
        b.mods = Collections.emptyList();
        b.uniques = Collections.emptyList();
        return b;
    }
}
