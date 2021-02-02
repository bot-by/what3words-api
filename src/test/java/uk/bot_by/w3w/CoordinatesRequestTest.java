package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("fast")
class CoordinatesRequestTest {

	@DisplayName("Create request by a 3 word address")
	@Test
	public void threeWordAddress() {
		// given
		ThreeWordAddress threeWordAddress = ThreeWordAddress.builder().words("first", "second", "third").build();

		// when
		CoordinatesRequest request = CoordinatesRequest.builder().words(threeWordAddress).build();

		// then
		assertAll("Coordinates request",
				() -> assertEquals("first", request.getWords().getFirst()),
				() -> assertEquals("second", request.getWords().getSecond()),
				() -> assertEquals("third", request.getWords().getThird()));
	}

	@DisplayName("Create request by an array")
	@Test
	public void array() {
		// when
		CoordinatesRequest request = CoordinatesRequest.builder().words(" first", "second ", " third ").build();

		// then
		assertAll("Coordinates request",
				() -> assertEquals("first", request.getWords().getFirst()),
				() -> assertEquals("second", request.getWords().getSecond()),
				() -> assertEquals("third", request.getWords().getThird()));
	}

	@DisplayName("Create request by a list")
	@Test
	public void list() {
		// when
		CoordinatesRequest request = CoordinatesRequest.builder().words(Arrays.asList(" first", "second ", " third ")).build();

		// then
		assertAll("Coordinates request",
				() -> assertEquals("first", request.getWords().getFirst()),
				() -> assertEquals("second", request.getWords().getSecond()),
				() -> assertEquals("third", request.getWords().getThird()));
	}

}