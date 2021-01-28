package uk.bot_by.w3w;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Collection;

public interface What3Words {

	String API_LOCATOR = "https://api.what3words.com";

	/**
	 * Get available languages.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key.
	 *
	 * @return available languages of What3Words API
	 * @see <a href="https://developer.what3words.com/public-api/docs#available-languages">What3Words: Available languages</a>
	 */
	@RequestLine("GET /v3/available-languages")
	Collection<Language> availableLanguages();

	/**
	 * Get available languages.
	 *
	 * @param key API key
	 * @return available languages of What3Words API
	 * @see <a href="https://developer.what3words.com/public-api/docs#available-languages">What3Words: Available languages</a>
	 */
	@RequestLine("GET /v3/available-languages")
	@Headers("X-Api-Key: {w3w-api-key}")
	Collection<Language> availableLanguages(@Param("w3w-api-key") String key);


	/**
	 * Get a three word address by its coordinates, in the  default language.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key.
	 *
	 * @param coordinates coordinates to convert to three word address}
	 * @return three word address
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-3wa">What3Words: Convert to 3 word address</a>
	 */
	@RequestLine("GET /v3/convert-to-3wa")
	ThreeWordAddress convertToAddress(@Param("coordinates") Coordinates coordinates);

	/**
	 * Get a three word address by its coordinates, in the  default language.
	 *
	 * @param key         API key
	 * @param coordinates coordinates to convert to three word address}
	 * @return three word address
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-3wa">What3Words: Convert to 3 word address</a>
	 */
	@RequestLine("GET /v3/convert-to-3wa")
	@Headers("X-Api-Key: {w3w-api-key}")
	ThreeWordAddress convertToAddress(@Param("w3w-api-key") String key, @Param("coordinates") Coordinates coordinates);

	/**
	 * Get a three word address by its coordinates, in the language of your choice.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key.
	 *
	 * @param coordinates coordinates to convert to three word address
	 * @param language    language of your choice
	 * @return three word address
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-3wa">What3Words: Convert to 3 word address</a>
	 */
	@RequestLine("GET /v3/convert-to-3wa")
	ThreeWordAddress convertToAddress(@Param("coordinates") Coordinates coordinates, @Param("language") Language language);

	/**
	 * Get a three word address by its coordinates, in the language of your choice.
	 *
	 * @param key         API key
	 * @param coordinates coordinates to convert to three word address
	 * @param language    language of your choice
	 * @return three word address
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-3wa">What3Words: Convert to 3 word address</a>
	 */
	@RequestLine("GET /v3/convert-to-3wa")
	@Headers("X-Api-Key: {w3w-api-key}")
	ThreeWordAddress convertToAddress(@Param("w3w-api-key") String key, @Param("coordinates") Coordinates coordinates,
									  @Param("language") Language language);

	/**
	 * Get coordinates by a three word address.
	 * <p>
	 * Use {@link KeyInterceptor} to set up API key.
	 *
	 * @param words three word address to convert to coordinates
	 * @return coordinates
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-coords">What3Words: Convert to coordinates</a>
	 */
	@RequestLine("GET /v3/convert-to-coordinates")
	Coordinates convertToCoordinates(@Param("words") ThreeWordAddress words);

	/**
	 * Get coordinates by a three word address.
	 *
	 * @param key   API key
	 * @param words three word address to convert to coordinates
	 * @return coordinates
	 * @see <a href="https://developer.what3words.com/public-api/docs#convert-to-coords">What3Words: Convert to coordinates</a>
	 */
	@RequestLine("GET /v3/convert-to-coordinates")
	@Headers("X-Api-Key: {w3w-api-key}")
	Coordinates convertToCoordinates(@Param("w3w-api-key") String key, @Param("words") ThreeWordAddress words);

}
