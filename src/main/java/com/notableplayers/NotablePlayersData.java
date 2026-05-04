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
		if (bundle.uniques == null)   bundle.uniques = Collections.emptyList();
		return bundle;
	}

	private static Bundle empty()
	{
		Bundle b = new Bundle();
		b.creators = Collections.emptyList();
		b.streamers = Collections.emptyList();
		b.uniques = Collections.emptyList();
		return b;
	}
}
