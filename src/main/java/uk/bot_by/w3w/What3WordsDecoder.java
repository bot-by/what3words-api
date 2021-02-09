/*
 * Copyright 2021 Witalij Berdinskich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.bot_by.w3w;

import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

public class What3WordsDecoder implements Decoder {

	private final Type coordinatesType;
	private final Type languagesType;
	private final Type threeWordAddressType;

	public What3WordsDecoder() {
		try {
			coordinatesType = What3Words.class.getMethod("convertToCoordinates", Map.class).getGenericReturnType();
			languagesType = What3Words.class.getMethod("availableLanguages").getGenericReturnType();
			threeWordAddressType = What3Words.class.getMethod("convertToAddress", Map.class).getGenericReturnType();
		} catch (NoSuchMethodException exception) {
			throw new IllegalStateException("could not initialize " + getClass().getName(), exception);
		}
	}

	@Override
	public Object decode(Response response, Type type) throws IOException, FeignException {
		if (null == response.body()) {
			throw new IllegalArgumentException("Empty body");
		}

		try (Reader reader = response.body().asReader(UTF_8)) {
			JSONObject json = new JSONObject(new JSONTokener(reader));

			if (coordinatesType.equals(type)) {
				return getCoordinates(json.getJSONObject("coordinates"));
			} else if (languagesType.equals(type)) {
				return getAvailableLanguages(json.getJSONArray("languages"));
			} else if (threeWordAddressType.equals(type)) {
				return getThreeWordAddress(json.getString("words"));
			}
		}

		throw new UnsupportedOperationException(String.format("Type %s is not supported", type));
	}

	private Object getAvailableLanguages(JSONArray languages) {
		Set<Language> availableLanguages = new HashSet<>();

		for (Object language : languages) {
			JSONObject item = (JSONObject) language;

			availableLanguages.add(Language.builder()
					.code(item.getString("code"))
					.name(item.getString("name"))
					.nativeName(item.getString("nativeName"))
					.build());
		}

		return availableLanguages;
	}

	private Coordinates getCoordinates(JSONObject coordinates) {
		return Coordinates.builder()
				.latitude(coordinates.getBigDecimal("lat"))
				.longitude(coordinates.getBigDecimal("lng"))
				.build();
	}

	private Object getThreeWordAddress(String words) {
		return ThreeWordAddress.builder()
				.words(words.split("\\."))
				.build();
	}

}
