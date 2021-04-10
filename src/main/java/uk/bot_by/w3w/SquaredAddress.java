package uk.bot_by.w3w;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * SquaredAddress represents response of <em>what3words</em> API.
 * <p>
 * Example of JSON:
 * <pre><code class="language-json">
 * {
 *   "country": "GB",
 *   "square": {
 *     "southwest": {
 *       "lng": -2.359613,
 *       "lat": 51.381037
 *     },
 *     "northeast": {
 *       "lng": -2.35957,
 *       "lat": 51.381064
 *     }
 *   },
 *   "nearestPlace": "Bath, Somerset",
 *   "coordinates": {
 *     "lng": -2.359591,
 *     "lat": 51.381051
 *   },
 *   "words": "spring.tops.issued",
 *   "language": "en",
 *   "map": "https://w3w.co/spring.tops.issued"
 * }
 * </code></pre>
 */
public interface SquaredAddress {

	/**
	 * Get a builder to constraint What3Words response.
	 *
	 * @return a builder
	 */
	static SquaredAddressBuilder builder() {
		return new SquaredAddressBuilder();
	}

	/**
	 * Get a country code.
	 *
	 * @return country code
	 * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1 alpha-2</a>
	 */
	String getCountry();

	/**
	 * Get a square 3x3 metres that is described by <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
	 *
	 * @return square
	 */
	Square getSquare();

	/**
	 * Get name of nearest city or town.
	 *
	 * @return nearest populated place.
	 */
	String getNearestPlace();

	/**
	 * Get coordinates of the centre of square.
	 *
	 * @return coordinates
	 */
	Coordinates getCoordinates();

	/**
	 * Get <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
	 *
	 * @return 3 word address
	 */
	Words getWords();

	/**
	 * Get language of 3 word address.
	 *
	 * @return language
	 */
	Language getLanguage();

	/**
	 * Get link to <em>what3words</em> map.
	 *
	 * @return link to map
	 */
	URL getMap();

	/**
	 * Basic implementation of {@link SquaredAddress}.
	 *
	 * @since 1.0.0
	 */
	class BasicSquaredAddress implements SquaredAddress {

		private final String country;
		private final Square square;
		private final String nearestPlace;
		private final Coordinates coordinates;
		private final Words words;
		private final Language language;
		private final URL map;

		private BasicSquaredAddress(SquaredAddressBuilder builder) {
			country = builder.country;
			square = builder.square;
			nearestPlace = builder.nearestPlace;
			coordinates = builder.coordinates;
			words = builder.words;
			language = builder.language;
			map = builder.map;
		}

		@Override
		public String getCountry() {
			return country;
		}

		@Override
		public Square getSquare() {
			return square;
		}

		@Override
		public String getNearestPlace() {
			return nearestPlace;
		}

		@Override
		public Coordinates getCoordinates() {
			return coordinates;
		}

		@Override
		public Words getWords() {
			return words;
		}

		@Override
		public Language getLanguage() {
			return language;
		}

		@Override
		public URL getMap() {
			return map;
		}

		@Override
		public String toString() {
			return new StringJoiner(",", "{", "}")
					       .add("country='" + country + "'")
					       .add("square=" + square)
					       .add("nearestPlace='" + nearestPlace + "'")
					       .add("coordinates=" + coordinates)
					       .add("words=" + words)
					       .add("language=" + language)
					       .add("map=" + map)
					       .toString();
		}

	}

	/**
	 * Helper to constraint What3Words response.
	 *
	 * @since 1.0.0
	 */
	class SquaredAddressBuilder {

		private String country;
		private Square square;
		private String nearestPlace;
		private Coordinates coordinates;
		private Words words;
		private Language language;
		private URL map;

		private SquaredAddressBuilder() {
		}

		/**
		 * Get What3Words response.
		 * <p>
		 * It checks that northeast and southwest coordinates are not null.
		 *
		 * @return a square of coordinates
		 * @throws NullPointerException if northeast or southwest are null
		 */
		public SquaredAddress build() throws NullPointerException {
			Objects.requireNonNull(country, "country is null");
			Objects.requireNonNull(square, "square is null");
			Objects.requireNonNull(nearestPlace, "nearest place is null");
			Objects.requireNonNull(coordinates, "coordinates is null");
			Objects.requireNonNull(words, "words is null");
			Objects.requireNonNull(language, "language is null");
			Objects.requireNonNull(map, "map is null");
			return new BasicSquaredAddress(this);
		}

		/**
		 * Set a country.
		 *
		 * @param country country
		 * @return the builder
		 */
		public SquaredAddressBuilder country(@NotNull String country) {
			this.country = country;
			return this;
		}

		/**
		 * Set a square.
		 *
		 * @param square square
		 * @return the builder
		 */
		public SquaredAddressBuilder square(@NotNull Square square) {
			this.square = square;
			return this;
		}

		/**
		 * Set a square.
		 *
		 * @param northeast northeast corner of square
		 * @param southwest southwest corner of square
		 * @return the builder
		 */
		public SquaredAddressBuilder square(@NotNull Coordinates northeast, @NotNull Coordinates southwest) {
			this.square = Square.builder()
			                    .northeast(northeast)
			                    .southwest(southwest)
			                    .build();
			return this;
		}

		/**
		 * Set nearest place.
		 *
		 * @param nearestPlace nearestPlace
		 * @return the builder
		 */
		public SquaredAddressBuilder nearestPlace(@NotNull String nearestPlace) {
			this.nearestPlace = nearestPlace;
			return this;
		}

		/**
		 * Set coordinates.
		 *
		 * @param coordinates coordinates
		 * @return the builder
		 */
		public SquaredAddressBuilder coordinates(@NotNull Coordinates coordinates) {
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
		 */
		public SquaredAddressBuilder coordinates(double latitude, double longitude) throws IllegalArgumentException {
			this.coordinates = Coordinates.builder()
			                              .latitude(latitude)
			                              .longitude(longitude)
			                              .build();
			return this;
		}

		/**
		 * Set coordinates.
		 *
		 * @param latitude  latitude
		 * @param longitude longitude
		 * @return the builder
		 * @throws IllegalArgumentException if latitude or longitude are out of range
		 */
		public SquaredAddressBuilder coordinates(@NotNull BigDecimal latitude, @NotNull BigDecimal longitude) throws IllegalArgumentException {
			this.coordinates = Coordinates.builder()
			                              .latitude(latitude)
			                              .longitude(longitude)
			                              .build();
			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
		 *
		 * @param words 3 word address
		 * @return the builder
		 */
		public SquaredAddressBuilder words(@NotNull Words words) {
			this.words = words;
			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string.
		 *
		 * @param words 3 word address
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 */
		public SquaredAddressBuilder words(@NotNull String words) throws IllegalArgumentException {
			this.words = Words.builder()
			                  .words(words)
			                  .build();
			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string array.
		 *
		 * @param words array with 3 elements
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 */
		public SquaredAddressBuilder words(@NotNull String... words) throws IllegalArgumentException {
			this.words = Words.builder()
			                  .words(words)
			                  .build();
			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string list.
		 *
		 * @param words list with 3 elements
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 */
		public SquaredAddressBuilder words(@NotNull List<String> words) throws IllegalArgumentException {
			this.words = Words.builder()
			                  .words(words)
			                  .build();
			return this;
		}

		/**
		 * Set language.
		 *
		 * @param language language
		 * @return the builder
		 */
		public SquaredAddressBuilder language(@NotNull Language language) {
			this.language = language;
			return this;
		}

		/**
		 * Set a map link.
		 *
		 * @param map a map link
		 * @return the builder
		 */
		public SquaredAddressBuilder map(@NotNull URL map) {
			this.map = map;
			return this;
		}

		/**
		 * Set a map link.
		 *
		 * @param mapLink a map link
		 * @return the builder
		 * @throws IllegalArgumentException if no protocol is specified, or an unknown protocol is found, or spec is null, or the parsed URL fails to
		 *                                  comply with the specific syntax of the associated protocol
		 */
		public SquaredAddressBuilder map(@NotNull String mapLink) throws IllegalArgumentException {
			try {
				this.map = new URL(mapLink);
			} catch (MalformedURLException exception) {
				throw new IllegalArgumentException("map link", exception);
			}
			return this;
		}

	}

}
