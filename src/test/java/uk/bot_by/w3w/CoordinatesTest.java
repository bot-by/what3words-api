package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("fast")
class CoordinatesTest {

	@DisplayName("Latitude is missing")
	@Test
	public void latitudeIsNull() {
		// given
		double longitude = -2.359591d;

		// when
		Exception exception = assertThrows(NullPointerException.class, () -> Coordinates.builder().longitude(longitude).build());

		// then
		assertEquals("latitude is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Latitude must be in the range")
	@ParameterizedTest
	@ValueSource(doubles = {-91d, 91d})
	public void latitudeIsOutOfRange(double latitude) {
		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> Coordinates.builder().latitude(latitude).build());

		// then
		assertEquals("latitude must be in the range of -90 to 90", exception.getMessage(), "exception message");
	}

	@DisplayName("Latitude is missing")
	@Test
	public void longitudeIsNull() {
		// given
		BigDecimal latitude = BigDecimal.valueOf(51.381051d);

		// when
		Exception exception = assertThrows(NullPointerException.class, () -> Coordinates.builder().latitude(latitude.doubleValue()).build());

		// then
		assertEquals("longitude is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Longitude must be in the range")
	@ParameterizedTest
	@ValueSource(doubles = {-181d, 181d})
	public void longitudeIsOutOfRange(double longitude) {
		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> Coordinates.builder().longitude(longitude).build());

		// then
		assertEquals("longitude must be in the range of -180 to 180", exception.getMessage(), "exception message");
	}

	@DisplayName("Builder")
	@Test
	public void builder() {
		// given
		BigDecimal latitude = BigDecimal.valueOf(51.381051d);
		BigDecimal longitude = BigDecimal.valueOf(-2.359591d);

		// when
		Coordinates coordinates = Coordinates.builder()
										  .latitude(latitude)
										  .longitude(longitude)
										  .build();

		// then
		assertAll("Coordinates",
				() -> assertEquals(latitude, coordinates.getLatitude(), "latitude"),
				() -> assertEquals(longitude, coordinates.getLongitude(), "longitude"));
	}

	@DisplayName("Coordinates as double values")
	@Test
	public void coordinatesAsDoubleValues() {
		// given
		BigDecimal latitude = BigDecimal.valueOf(51.381051d);
		BigDecimal longitude = BigDecimal.valueOf(-2.359591d);

		// when
		Coordinates coordinates = Coordinates.builder()
										  .coordinates(51.381051d, -2.359591d)
										  .build();

		// then
		assertAll("Coordinates",
				() -> assertEquals(latitude, coordinates.getLatitude(), "latitude"),
				() -> assertEquals(longitude, coordinates.getLongitude(), "longitude"));
	}

	@DisplayName("Coordinates as BigDecimal values")
	@Test
	public void coordinatesAsBigDecimalValues() {
		// given
		BigDecimal latitude = BigDecimal.valueOf(51.381051d);
		BigDecimal longitude = BigDecimal.valueOf(-2.359591d);

		// when
		Coordinates coordinates = Coordinates.builder()
										  .coordinates(latitude, longitude)
										  .build();

		// then
		assertAll("Coordinates",
				() -> assertEquals(latitude, coordinates.getLatitude(), "latitude"),
				() -> assertEquals(longitude, coordinates.getLongitude(), "longitude"));
	}

	@DisplayName("To string")
	@Test
	public void string() {
		// given
		Coordinates coordinates = Coordinates.builder()
										  .latitude(51.381051d)
										  .longitude(-2.359591d)
										  .build();

		// when and then
		assertEquals("51.381051,-2.359591", coordinates.toString());
	}

}