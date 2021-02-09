package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("fast")
class ThreeWordAddressRequestTest {

	@DisplayName("Create request by coordinates")
	@Test
	public void coordinates() {
		// given
		ThreeWordAddressRequest.ThreeWordAddressRequestBuilder builder = ThreeWordAddressRequest.builder();
		Coordinates coordinates = Coordinates.builder().latitude(51.381051d).longitude(-2.359591d).build();
		BigDecimal expectedLatitude = BigDecimal.valueOf(51.381051d);
		BigDecimal expectedLongitude = BigDecimal.valueOf(-2.359591d);

		// when
		ThreeWordAddressRequest request = builder.coordinates(coordinates).build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(expectedLatitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(expectedLongitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertNull(request.getLanguage()));
	}

	@DisplayName("Create request by double values")
	@Test
	public void doubleValues() {
		// given
		ThreeWordAddressRequest.ThreeWordAddressRequestBuilder builder = ThreeWordAddressRequest.builder();
		BigDecimal expectedLatitude = BigDecimal.valueOf(51.381051d);
		BigDecimal expectedLongitude = BigDecimal.valueOf(-2.359591d);

		// when
		ThreeWordAddressRequest request = builder.coordinates(51.381051d, -2.359591d).build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(expectedLatitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(expectedLongitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertNull(request.getLanguage()));
	}

	@DisplayName("Create request by BigDecimal values")
	@Test
	public void numberValues() {
		// given
		ThreeWordAddressRequest.ThreeWordAddressRequestBuilder builder = ThreeWordAddressRequest.builder();
		BigDecimal latitude = BigDecimal.valueOf(51.381051d);
		BigDecimal longitude = BigDecimal.valueOf(-2.359591d);

		// when
		ThreeWordAddressRequest request = builder.coordinates(latitude, longitude).build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(latitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(longitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertNull(request.getLanguage()));
	}

	@DisplayName("Create request by coordinates and language")
	@Test
	public void language() {
		// given
		ThreeWordAddressRequest.ThreeWordAddressRequestBuilder builder = ThreeWordAddressRequest.builder();
		Coordinates coordinates = Coordinates.builder().latitude(51.381051d).longitude(-2.359591d).build();
		BigDecimal expectedLatitude = BigDecimal.valueOf(51.381051d);
		BigDecimal expectedLongitude = BigDecimal.valueOf(-2.359591d);
		Language language = Language.builder().code("al").name("Alien Language").nativeName("Alienese").build();

		// when
		ThreeWordAddressRequest request = builder.coordinates(coordinates).language(language).build();

		// then
		assertAll("3 word address request",
				() -> assertEquals(expectedLatitude, request.getCoordinates().getLatitude(), "latitude"),
				() -> assertEquals(expectedLongitude, request.getCoordinates().getLongitude(), "longitude"),
				() -> assertEquals(language, request.getLanguage(), "language"));
	}

}