package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("fast")
class CoordinatesTest {

	@DisplayName("Latitude is missing")
	@Test
	public void latitude() {
		// given
		BigDecimal longitude = BigDecimal.valueOf(-2.359591d);

		// when
		Exception exception = assertThrows(NullPointerException.class, () -> Coordinates.builder().longitude(longitude.doubleValue()).build());

		// then
		assertEquals("latitude is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Latitude is missing")
	@Test
	public void longitude() {
		// given
		BigDecimal latitude = BigDecimal.valueOf(51.381051d);

		// when
		Exception exception = assertThrows(NullPointerException.class, () -> Coordinates.builder().latitude(latitude.doubleValue()).build());

		// then
		assertEquals("longitude is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Builder")
	@Test
	public void builder() {
		// given
		BigDecimal latitude = BigDecimal.valueOf(51.381051d);
		BigDecimal longitude = BigDecimal.valueOf(-2.359591d);

		// when
		Coordinates coordinates = Coordinates.builder().latitude(latitude.doubleValue()).longitude(longitude.doubleValue()).build();

		// then
		assertAll("Coordinates",
				() -> assertEquals(latitude, coordinates.getLatitude(), "latitude"),
				() -> assertEquals(longitude, coordinates.getLongitude(), "longitude"));
	}

	@DisplayName("To string")
	@Test
	public void string() {
		// given
		Coordinates coordinates = Coordinates.builder().latitude(51.381051d).longitude(-2.359591d).build();

		// when and then
		assertEquals("51.381051,-2.359591", coordinates.toString());
	}

}