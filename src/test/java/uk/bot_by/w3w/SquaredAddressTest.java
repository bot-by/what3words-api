package uk.bot_by.w3w;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("fast")
class SquaredAddressTest {

	private Coordinates coordinates;
	private Language language;
	private Coordinates northeast;
	private Coordinates southwest;
	private Square square;
	private Words words;

	@BeforeEach
	public void setUp() {
		coordinates = Coordinates.builder()
		                         .latitude(50.01d)
		                         .longitude(-2.02d)
		                         .build();
		language = Language.builder()
		                   .code("aa")
		                   .name("name")
		                   .nativeName("native name")
		                   .build();
		northeast = Coordinates.builder()
		                       .latitude(49.39d)
		                       .longitude(-1.01d)
		                       .build();
		southwest = Coordinates.builder()
		                       .latitude(51.03d)
		                       .longitude(1.09d)
		                       .build();
		square = Square.builder()
		               .northeast(northeast)
		               .southwest(southwest)
		               .build();
		words = Words.builder()
		             .words("spring.tops.issued")
		             .build();
	}

	@DisplayName("Builder")
	@Test
	public void builder() throws MalformedURLException {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

		// when
		SquaredAddress squaredAddress =
				builder.country("AA")
				       .square(square)
				       .nearestPlace("place")
				       .coordinates(coordinates)
				       .words(words)
				       .language(language)
				       .map(new URL("https://example.com"))
				       .build();

		// then
		assertSquaredAddress(squaredAddress);
	}

	@DisplayName("Coordinates as double values")
	@Test
	public void coordinatesAsDoubleValues() throws MalformedURLException {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

		// when
		SquaredAddress squaredAddress =
				builder.country("AA")
				       .square(square)
				       .nearestPlace("place")
				       .coordinates(50.01d, -2.02d)
				       .words(words)
				       .language(language)
				       .map(new URL("https://example.com"))
				       .build();

		// then
		assertSquaredAddress(squaredAddress);
	}

	@DisplayName("Coordinates as BigDecimal values")
	@Test
	public void coordinatesAsBigDecimalValues() throws MalformedURLException {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

		// when
		SquaredAddress squaredAddress =
				builder.country("AA")
				       .square(square)
				       .nearestPlace("place")
				       .coordinates(BigDecimal.valueOf(50.01d), BigDecimal.valueOf(-2.02d))
				       .words(words)
				       .language(language)
				       .map(new URL("https://example.com"))
				       .build();

		// then
		assertSquaredAddress(squaredAddress);
	}

	@DisplayName("Square as corners")
	@Test
	public void squareAsCorners() throws MalformedURLException {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

		// when
		SquaredAddress squaredAddress =
				builder.country("AA")
				       .square(northeast, southwest)
				       .nearestPlace("place")
				       .coordinates(coordinates)
				       .words(words)
				       .language(language)
				       .map(new URL("https://example.com"))
				       .build();

		// then
		assertSquaredAddress(squaredAddress);
	}

	@DisplayName("Words as array")
	@Test
	public void wordsAsArray() throws MalformedURLException {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

		// when
		SquaredAddress squaredAddress =
				builder.country("AA")
				       .square(square)
				       .nearestPlace("place")
				       .coordinates(coordinates)
				       .words("spring", "tops", "issued")
				       .language(language)
				       .map(new URL("https://example.com"))
				       .build();

		// then
		assertSquaredAddress(squaredAddress);
	}

	@DisplayName("Words as list")
	@Test
	public void wordsAsList() throws MalformedURLException {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

		// when
		SquaredAddress squaredAddress =
				builder.country("AA")
				       .square(square)
				       .nearestPlace("place")
				       .coordinates(coordinates)
				       .words(Arrays.asList("spring", "tops", "issued"))
				       .language(language)
				       .map(new URL("https://example.com"))
				       .build();

		// then
		assertSquaredAddress(squaredAddress);
	}

	@DisplayName("Words as string")
	@Test
	public void wordsAsString() throws MalformedURLException {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

		// when
		SquaredAddress squaredAddress =
				builder.country("AA")
				       .square(square)
				       .nearestPlace("place")
				       .coordinates(coordinates)
				       .words("///spring・tops・issued")
				       .language(language)
				       .map(new URL("https://example.com"))
				       .build();

		// then
		assertSquaredAddress(squaredAddress);
	}

	@DisplayName("Map link")
	@Test
	public void mapLink() {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();

		// when
		SquaredAddress squaredAddress =
				builder.country("AA")
				       .square(square)
				       .nearestPlace("place")
				       .coordinates(coordinates)
				       .words(words)
				       .language(language)
				       .map("https://example.com")
				       .build();

		// then
		assertSquaredAddress(squaredAddress);
	}

	@DisplayName("Bad map link")
	@Test
	public void badMapLink() {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder()
		                                                             .country("AA")
		                                                             .square(square)
		                                                             .nearestPlace("place")
		                                                             .coordinates(coordinates)
		                                                             .words(words)
		                                                             .language(language);

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.map("qwerty!@#$%^"));

		// then
		assertEquals("map link", exception.getMessage(), "exception message");
	}

	@DisplayName("Missed field")
	@ParameterizedTest(name = "{arguments}")
	@CsvSource({
			"  ,square,place,coordinates,spring。tops。issued,aa,https://example.com, country is null",
			"AA,      ,place,coordinates,spring。tops。issued,aa,https://example.com, square is null",
			"AA,square,     ,coordinates,spring。tops。issued,aa,https://example.com, nearest place is null",
			"AA,square,place,           ,spring。tops。issued,aa,https://example.com, coordinates is null",
			"AA,square,place,coordinates,                  ,aa,https://example.com, words is null",
			"AA,square,place,coordinates,spring。tops。issued,  ,https://example.com, language is null",
			"AA,square,place,coordinates,spring。tops。issued,aa,                   , map is null"})
	public void missedField(String country, String square, String nearestPlace, String coordinates, String words,
	                        String languageCode, String mapLink, String expectedMessage) {
		// given
		SquaredAddress.SquaredAddressBuilder builder = SquaredAddress.builder();
		Optional.ofNullable(country).ifPresent(builder::country);
		Optional.ofNullable(square).ifPresent(empty -> builder.square(northeast, southwest));
		Optional.ofNullable(nearestPlace).ifPresent(builder::nearestPlace);
		Optional.ofNullable(coordinates).ifPresent(empty -> builder.coordinates(BigDecimal.valueOf(50.01d), BigDecimal.valueOf(-2.02d)));
		Optional.ofNullable(words).ifPresent(builder::words);
		Optional.ofNullable(languageCode).ifPresent(empty -> builder.language(Language.builder().code(languageCode).build()));
		Optional.ofNullable(mapLink).ifPresent(builder::map);

		// when
		Exception exception = assertThrows(NullPointerException.class, builder::build);

		// then
		assertEquals(expectedMessage, exception.getMessage(), "exception message");
	}

	@DisplayName("To string")
	@Test
	public void string() {
		// given
		SquaredAddress squaredAddress = SquaredAddress.builder()
		                                              .country("AA")
		                                              .square(square)
		                                              .nearestPlace("place")
		                                              .coordinates(coordinates)
		                                              .words(words)
		                                              .language(language)
		                                              .map("https://example.com")
		                                              .build();

		// when and then
		assertEquals("{country='AA',square={northeast:49.39,-1.01;southwest:51.03,1.09},nearestPlace='place',coordinates=50.01,-2.02," +
				             "words=spring.tops.issued,language=aa,map=https://example.com}", squaredAddress.toString());
	}

	private void assertSquaredAddress(SquaredAddress squaredAddress) {
		assertAll("Squared address, full response of What3Words",
				() -> assertEquals("AA", squaredAddress.getCountry(), "country"),
				() -> assertEquals(square, squaredAddress.getSquare(), "square"),
				() -> assertEquals("place", squaredAddress.getNearestPlace(), "nearest place"),
				() -> assertEquals(coordinates, squaredAddress.getCoordinates(), "coordinates"),
				() -> assertEquals(words, squaredAddress.getWords(), "words"),
				() -> assertEquals(language, squaredAddress.getLanguage(), "language"),
				() -> assertEquals("https://example.com", squaredAddress.getMap().toExternalForm(), "map"));
	}

}