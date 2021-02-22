package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("fast")
class WordsRequestTest {

	@DisplayName("Create request by coordinates")
	@Test
	public void coordinates() {
		// given
		WordsRequest.WordsRequestBuilder builder = WordsRequest.builder();
		Coordinates coordinates = Coordinates.builder()
										  .latitude(51.381051d)
										  .longitude(-2.359591d)
										  .build();
		BigDecimal expectedLatitude = BigDecimal.valueOf(51.381051d);
		BigDecimal expectedLongitude = BigDecimal.valueOf(-2.359591d);

		// when
		WordsRequest request = builder.coordinates(coordinates).build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(expectedLatitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(expectedLongitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertNull(request.getLanguage(), "language"));
	}

	@DisplayName("Create request by double values")
	@Test
	public void doubleValues() {
		// given
		WordsRequest.WordsRequestBuilder builder = WordsRequest.builder();
		BigDecimal expectedLatitude = BigDecimal.valueOf(51.381051d);
		BigDecimal expectedLongitude = BigDecimal.valueOf(-2.359591d);

		// when
		WordsRequest request = builder.coordinates(51.381051d, -2.359591d).build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(expectedLatitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(expectedLongitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertNull(request.getLanguage(), "language"));
	}

	@DisplayName("Create request by BigDecimal values")
	@Test
	public void numberValues() {
		// given
		WordsRequest.WordsRequestBuilder builder = WordsRequest.builder();
		BigDecimal latitude = BigDecimal.valueOf(51.381051d);
		BigDecimal longitude = BigDecimal.valueOf(-2.359591d);

		// when
		WordsRequest request = builder.coordinates(latitude, longitude).build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(latitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(longitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertNull(request.getLanguage(), "language"));
	}

	@DisplayName("Create request by coordinates and language")
	@Test
	public void language() {
		// given
		WordsRequest.WordsRequestBuilder builder = WordsRequest.builder();
		Coordinates coordinates = Coordinates.builder()
										  .latitude(51.381051d)
										  .longitude(-2.359591d)
										  .build();
		BigDecimal expectedLatitude = BigDecimal.valueOf(51.381051d);
		BigDecimal expectedLongitude = BigDecimal.valueOf(-2.359591d);
		Language language = Language.builder()
									.code("al")
									.name("Alien Language")
									.nativeName("Alienese")
									.build();

		// when
		WordsRequest request = builder.coordinates(coordinates).language(language).build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(expectedLatitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(expectedLongitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertEquals(language, request.getLanguage(), "language"));
	}

	@DisplayName("Create request by coordinates and language code")
	@Test
	public void languageCode() {
		// given
		WordsRequest.WordsRequestBuilder builder = WordsRequest.builder();
		Coordinates coordinates = Coordinates.builder()
										  .latitude(51.381051d)
										  .longitude(-2.359591d)
										  .build();
		BigDecimal expectedLatitude = BigDecimal.valueOf(51.381051d);
		BigDecimal expectedLongitude = BigDecimal.valueOf(-2.359591d);
		Language expectedLanguage = Language.builder().code("al").build();

		// when
		WordsRequest request = builder.coordinates(coordinates).language("al").build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(expectedLatitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(expectedLongitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertEquals(expectedLanguage, request.getLanguage(), "language"));
	}

}