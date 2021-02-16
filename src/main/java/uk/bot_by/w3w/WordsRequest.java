package uk.bot_by.w3w;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Strict holder of query parameters for the <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> request {@link What3Words#convertToAddress(String, WordsRequest)}
 *
 * @since 1.0.0
 */
public class WordsRequest {

	private final Coordinates coordinates;
	private final Language language;

	private WordsRequest(WordsRequestBuilder builder) {
		coordinates = builder.coordinates;
		language = builder.language;
	}

	/**
	 * Get a builder to constraint <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> query parameters.
	 *
	 * @return a builder
	 */
	public static WordsRequestBuilder builder() {
		return new WordsRequestBuilder();
	}

	/**
	 * Get coordinates.
	 *
	 * @return coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Get language.
	 *
	 * @return language
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Helper to constraint <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> query parameters.
	 *
	 * @since 1.0.0
	 */
	public static class WordsRequestBuilder {

		private Coordinates coordinates;
		private Language language;

		private WordsRequestBuilder() {
		}

		/**
		 * Build <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> query parameters.
		 *
		 * @return 3 word address query parameters
		 * @throws NullPointerException if coordinates is null
		 * @see uk.bot_by.w3w.Coordinates.CoordinatesBuilder#build()
		 */
		public WordsRequest build() throws NullPointerException {
			Objects.requireNonNull(coordinates, "coordinate is null");
			return new WordsRequest(this);
		}

		/**
		 * Set coordinates.
		 *
		 * @param coordinates coordinates
		 * @return the builder
		 */
		public WordsRequestBuilder coordinates(@NotNull Coordinates coordinates) {
			this.coordinates = coordinates;
			return this;
		}

		/**
		 * Set coordinates.
		 *
		 * @param latitude  latitude
		 * @param longitude longitude
		 * @return the builder
		 * @throws IllegalArgumentException if latitude or longitude are out of range
		 * @see uk.bot_by.w3w.Coordinates.CoordinatesBuilder#latitude(double)
		 * @see uk.bot_by.w3w.Coordinates.CoordinatesBuilder#longitude(double)
		 */
		public WordsRequestBuilder coordinates(double latitude, double longitude) throws IllegalArgumentException {
			coordinates = Coordinates.builder().latitude(latitude).longitude(longitude).build();
			return this;
		}

		/**
		 * Set coordinates.
		 *
		 * @param latitude  latitude
		 * @param longitude longitude
		 * @return the builder
		 * @throws IllegalArgumentException if latitude or longitude are out of range
		 * @see uk.bot_by.w3w.Coordinates.CoordinatesBuilder#latitude(double)
		 * @see uk.bot_by.w3w.Coordinates.CoordinatesBuilder#longitude(double)
		 */
		public WordsRequestBuilder coordinates(@NotNull BigDecimal latitude, @NotNull BigDecimal longitude) throws IllegalArgumentException {
			coordinates = Coordinates.builder().latitude(latitude).longitude(longitude).build();
			return this;
		}

		/**
		 * Set language.
		 *
		 * @param language language
		 * @return the builder
		 */
		public WordsRequestBuilder language(@NotNull Language language) {
			this.language = language;
			return this;
		}

	}

}
