package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("fast")
class ThreeWordAddressTest {

	@DisplayName("Array is too small")
	@Test
	public void lengthOfArray() {
		// given
		String[] words = {"spring", "issued"};

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> ThreeWordAddress.builder().words(words).build());

		// then
		assertEquals("three words is required", exception.getMessage(), "exception message");
	}

	@DisplayName("List is too small")
	@Test
	public void sizeOfList() {
		// given
		List<String> words = Arrays.asList("spring", "issued");

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> ThreeWordAddress.builder().words(words).build());

		// then
		assertEquals("three words is required", exception.getMessage(), "exception message");
	}

	@DisplayName("First word is missing")
	@Test
	public void first() {
		// when
		Exception exception = assertThrows(NullPointerException.class, () -> ThreeWordAddress.builder().second("tops").third("issued").build());

		// then
		assertEquals("first word is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Second word is missing")
	@Test
	public void second() {
		// when
		Exception exception = assertThrows(NullPointerException.class, () -> ThreeWordAddress.builder().first("spring").third("issued").build());

		// then
		assertEquals("second word is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Second word is missing")
	@Test
	public void third() {
		// when
		Exception exception = assertThrows(NullPointerException.class, () -> ThreeWordAddress.builder().first("spring").second("tops").build());

		// then
		assertEquals("third word is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Builder")
	@Test
	public void builder() {
		// when
		ThreeWordAddress threeWordAddress = ThreeWordAddress.builder().words(new String[]{"spring", "tops", "issued"}).build();

		// then
		assertAll("Three word address",
				() -> assertEquals("spring", threeWordAddress.getFirst(), "first"),
				() -> assertEquals("tops", threeWordAddress.getSecond(), "second"),
				() -> assertEquals("issued", threeWordAddress.getThird(), "third"));
	}

	@DisplayName("To string")
	@Test
	public void testToString() {
		// given
		ThreeWordAddress threeWordAddress = ThreeWordAddress.builder().words(new String[]{"spring", "tops", "issued"}).build();

		// when and then
		assertEquals("spring.tops.issued", threeWordAddress.toString());
	}

}