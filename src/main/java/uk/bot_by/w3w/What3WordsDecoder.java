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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

public class What3WordsDecoder implements Decoder {

	@Override
	public Object decode(Response response, Type type) throws IOException, FeignException {
		if (null == response.body()) {
			throw new IllegalArgumentException("Empty body");
		}

		try (Reader reader = response.body().asReader(UTF_8)) {
			JSONObject json = new JSONObject(new JSONTokener(reader));

			if (json.has("error")) {
				JSONObject error = json.getJSONObject("error");

				throw new What3WordsException(response.status(), error.getString("code"), error.getString("message"));
			}

			if (Coordinates.class.equals(type)) {
				return getCoordinates(json.getJSONObject("coordinates"));
			} else if (Collection.class.equals(type)) {
				return getAvailableLanguages(json.getJSONArray("languages"));
			} else if (ThreeWordAddress.class.equals(type)) {
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
