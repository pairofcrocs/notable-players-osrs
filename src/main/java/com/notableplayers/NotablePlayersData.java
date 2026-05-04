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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
		List<Entry> uniques;
	}

	private static final String RESOURCE = "/com/notableplayers/notable_players.json";

	// While the repo is private this URL 404s and the plugin falls back to the
	// bundled list. The fetch starts working as soon as the repo (or this file)
	// is publicly readable.
	private static final HttpUrl REMOTE_URL = HttpUrl.parse(
		"https://raw.githubusercontent.com/pairofcrocs/notable-players/main/src/main/resources/com/notableplayers/notable_players.json");

	private final Gson gson;
	private final OkHttpClient httpClient;
	private final Bundle bundled;
	private volatile Bundle remote;

	@Inject
	NotablePlayersData(Gson gson, OkHttpClient httpClient)
	{
		this.gson = gson;
		this.httpClient = httpClient;
		this.bundled = loadBundled();
	}

	Bundle current()
	{
		Bundle b = remote;
		return b != null ? b : bundled;
	}

	void refresh(Runnable onUpdated)
	{
		if (REMOTE_URL == null)
		{
			return;
		}
		Request req = new Request.Builder().url(REMOTE_URL).build();
		httpClient.newCall(req).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				log.warn("Remote notable players fetch failed: {}", e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response)
			{
				try (Response r = response)
				{
					if (!r.isSuccessful() || r.body() == null)
					{
						log.warn("Remote notable players fetch returned HTTP {}", r.code());
						return;
					}
					Bundle parsed = gson.fromJson(r.body().charStream(), Bundle.class);
					if (parsed == null)
					{
						return;
					}
					normalize(parsed);
					remote = parsed;
					log.info("Loaded remote notable players list");
					if (onUpdated != null)
					{
						onUpdated.run();
					}
				}
				catch (JsonSyntaxException | IllegalStateException e)
				{
					log.warn("Failed to parse remote notable players list", e);
				}
			}
		});
	}

	private Bundle loadBundled()
	{
		try (InputStream in = NotablePlayersData.class.getResourceAsStream(RESOURCE))
		{
			if (in == null)
			{
				log.warn("Bundled notable player list not found at {}", RESOURCE);
				return empty();
			}
			Bundle b = gson.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), Bundle.class);
			if (b == null)
			{
				return empty();
			}
			normalize(b);
			return b;
		}
		catch (IOException | JsonSyntaxException e)
		{
			log.warn("Failed to load bundled notable player list", e);
			return empty();
		}
	}

	private static void normalize(Bundle b)
	{
		if (b.creators == null)
		{
			b.creators = Collections.emptyList();
		}
		if (b.streamers == null)
		{
			b.streamers = Collections.emptyList();
		}
		if (b.uniques == null)
		{
			b.uniques = Collections.emptyList();
		}
	}

	private static Bundle empty()
	{
		Bundle b = new Bundle();
		normalize(b);
		return b;
	}
}
