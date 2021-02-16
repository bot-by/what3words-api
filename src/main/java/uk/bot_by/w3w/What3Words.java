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
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

/**
 * <em>what3words</em> API.
 *
 * @see <a href="https://developer.what3words.com/public-api/docs"><em>what3words</em>: API Reference Docs</a>
 * @since 1.0.0
 */
@Headers("Accept: application/json")
public interface What3Words {

	String API_LOCATOR = "https://api.what3words.com";

	/**
	 * Get available languages.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key.
	 *
	 * @return available languages of what3words API
	 * @throws FeignException      if a remote server is not available or returns unknown error
	 * @throws What3WordsException if <em>what3words</em> API returns error
	 * @see <a href="https://developer.what3words.com/public-api/docs#available-languages">what3words: Available languages</a>
	 */
	@RequestLine("GET /v3/available-languages")
	Collection<Language> availableLanguages() throws FeignException, What3WordsException;

	/**
	 * Get available languages.
	 * <p>
	 * It overrides API key of {@link KeyInterceptor} if it is used.
	 *
	 * @param key API key, will be added to request as the header {@code X-Api-Key}
	 * @return available languages of what3words API
	 * @throws FeignException      if a remote server is not available or returns unknown error
	 * @throws What3WordsException if <em>what3words</em> API returns error
	 * @see <a href="https://developer.what3words.com/public-api/docs#available-languages">what3words: Available languages</a>
	 */
	@RequestLine("GET /v3/available-languages")
	@Headers("X-Api-Key: {w3w-api-key}")
	Collection<Language> availableLanguages(@NotNull @Param("w3w-api-key") String key) throws FeignException, What3WordsException;


	/**
	 * Get a <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by its coordinates.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key or add the query parameter {@code key}.
	 *
	 * @param queryParameters query parameters, coordinates are required and language is optional
	 * @return 3 word address
	 * @throws FeignException      if a remote server is not available or returns unknown error
	 * @throws What3WordsException if <em>what3words</em> API returns error
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-3wa">what3words: Convert to 3 word address</a>
	 */
	@RequestLine("GET /v3/convert-to-3wa")
	Words convertToAddress(@QueryMap Map<String, Object> queryParameters) throws FeignException, What3WordsException;

	/**
	 * Get a <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by its coordinates.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key.
	 *
	 * @param wordsRequest query parameters, coordinates are required and language is optional
	 * @return 3 word address
	 * @throws FeignException      if a remote server is not available or returns unknown error
	 * @throws What3WordsException if <em>what3words</em> API returns error
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-3wa">what3words: Convert to 3 word address</a>
	 */
	@RequestLine("GET /v3/convert-to-3wa")
	Words convertToAddress(@QueryMap WordsRequest wordsRequest) throws FeignException, What3WordsException;

	/**
	 * Get a <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by its coordinates.
	 * <p>
	 * It overrides API key of {@link KeyInterceptor} if it is used.
	 *
	 * @param key                     API key, will be added as the header {@code X-Api-Key}
	 * @param wordsRequest query parameters, coordinates are required and language is optional
	 * @return 3 word address
	 * @throws FeignException      if a remote server is not available or returns unknown error
	 * @throws What3WordsException if <em>what3words</em> API returns error
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-3wa">what3words: Convert to 3 word address</a>
	 */
	@RequestLine("GET /v3/convert-to-3wa")
	@Headers("X-Api-Key: {w3w-api-key}")
	Words convertToAddress(@NotNull @Param("w3w-api-key") String key, @QueryMap WordsRequest wordsRequest)
			throws FeignException, What3WordsException;

	/**
	 * Get coordinates by a <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key or add the query parameter {@code key}.
	 *
	 * @param queryParameters query parameters, words is required
	 * @return coordinates
	 * @throws FeignException      if a remote server is not available or returns unknown error
	 * @throws What3WordsException if <em>what3words</em> API returns error
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-coords">what3words: Convert to coordinates</a>
	 */
	@RequestLine("GET /v3/convert-to-coordinates")
	Coordinates convertToCoordinates(@QueryMap Map<String, Object> queryParameters) throws FeignException, What3WordsException;

	/**
	 * Get coordinates by a <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key.
	 *
	 * @param coordinatesRequest query parameters, words is required
	 * @return coordinates
	 * @throws FeignException      if a remote server is not available or returns unknown error
	 * @throws What3WordsException if <em>what3words</em> API returns error
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-coords">what3words: Convert to coordinates</a>
	 */
	@RequestLine("GET /v3/convert-to-coordinates")
	Coordinates convertToCoordinates(@QueryMap CoordinatesRequest coordinatesRequest) throws FeignException, What3WordsException;

	/**
	 * Get coordinates by a <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
	 * <p>
	 * It overrides API key of {@link KeyInterceptor} if it is used.
	 *
	 * @param key                API key
	 * @param coordinatesRequest query parameters, words is required
	 * @return coordinates
	 * @throws FeignException      if a remote server is not available or returns unknown error
	 * @throws What3WordsException if <em>what3words</em> API returns error
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-coords">what3words: Convert to coordinates</a>
	 */
	@RequestLine("GET /v3/convert-to-coordinates")
	@Headers("X-Api-Key: {w3w-api-key}")
	Coordinates convertToCoordinates(@Param("w3w-api-key") String key, @QueryMap CoordinatesRequest coordinatesRequest)
			throws FeignException, What3WordsException;

}
