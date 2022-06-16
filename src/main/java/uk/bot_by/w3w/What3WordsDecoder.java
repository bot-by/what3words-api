/*
 * Copyright 2021,2022 Witalij Berdinskich
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

import static java.nio.charset.StandardCharsets.UTF_8;

import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Decode response of <em>what3words</em> API.
 *
 * @since 1.0.0
 */
public class What3WordsDecoder implements Decoder {

  private final Type languagesType;
  private final Type squaredAddressType;

  public What3WordsDecoder() {
    try {
      languagesType = What3Words.class.getMethod("availableLanguages").getGenericReturnType();
      squaredAddressType = SquaredAddress.class;
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

      if (languagesType.equals(type)) {
        return getAvailableLanguages(json.getJSONArray("languages"));
      } else if (squaredAddressType.equals(type)) {
        return getSquaredAddress(json);
      }
    }

    throw new UnsupportedOperationException(String.format("Type %s is not supported", type));
  }

  private Object getAvailableLanguages(JSONArray languages) {
    Set<Language> availableLanguages = new HashSet<>();

    for (Object language : languages) {
      JSONObject item = (JSONObject) language;

      availableLanguages.add(
          Language.builder().code(item.getString("code")).name(item.getString("name"))
              .nativeName(item.getString("nativeName")).build());
    }

    return availableLanguages;
  }

  private SquaredAddress getSquaredAddress(JSONObject squaredAddress) {
    SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

    builder.country(squaredAddress.getString("country"));
    builder.square(getSquare(squaredAddress.getJSONObject("square")));
    builder.nearestPlace(squaredAddress.getString("nearestPlace"));
    builder.coordinates(getCoordinates(squaredAddress.getJSONObject("coordinates")));
    builder.words(squaredAddress.getString("words"));
    builder.language(getLanguage(squaredAddress.getString("language")));
    builder.map(squaredAddress.getString("map"));

    return builder.build();
  }

  private Coordinates getCoordinates(JSONObject coordinates) {
    return Coordinates.builder().latitude(coordinates.getBigDecimal("lat"))
        .longitude(coordinates.getBigDecimal("lng")).build();
  }

  private Language getLanguage(String language) {
    return Language.builder().code(language).build();
  }

  private Square getSquare(JSONObject square) {
    return Square.builder().northeast(getCoordinates(square.getJSONObject("northeast")))
        .southwest(getCoordinates(square.getJSONObject("southwest"))).build();
  }

}
