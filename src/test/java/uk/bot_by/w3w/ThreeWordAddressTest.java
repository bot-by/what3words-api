package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

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
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words(words).build());

		// then
		assertEquals("3 words are required", exception.getMessage(), "exception message");
	}

	@DisplayName("List is too small")
	@Test
	public void sizeOfList() {
		// given
		List<String> words = Arrays.asList("spring", "issued");
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words(words).build());

		// then
		assertEquals("3 words are required", exception.getMessage(), "exception message");
	}

	@DisplayName("Bad word")
	@Test
	public void badWord() {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words(Arrays.asList("first", "$econd", "third")));

		// then
		assertEquals("bad word", exception.getMessage(), "exception message");
	}


	@DisplayName("Bad words")
	@Test
	public void badWords() {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words("spring・top$・issued"));

		// then
		assertEquals("bad word", exception.getMessage(), "exception message");
	}

	@DisplayName("Word is blank or empty")
	@ParameterizedTest(name = "word: \"{arguments}\"")
	@EmptySource
	@ValueSource(strings = " ")
	public void emptyWord(String word) {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words(Arrays.asList("first", word, "third")));

		// then
		assertEquals("empty word", exception.getMessage(), "exception message");
	}


	@DisplayName("Words is blank or empty")
	@ParameterizedTest(name = "words: \"{arguments}\"")
	@EmptySource
	@ValueSource(strings = " ")
	public void emptyWords(String words) {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words(words));

		// then
		assertEquals("empty words", exception.getMessage(), "exception message");
	}

	@DisplayName("First word is missing")
	@Test
	public void first() {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(NullPointerException.class, () -> builder.second("tops").third("issued").build());

		// then
		assertEquals("first word is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Second word is missing")
	@Test
	public void second() {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(NullPointerException.class, () -> builder.first("spring").third("issued").build());

		// then
		assertEquals("second word is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Third word is missing")
	@Test
	public void third() {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		Exception exception = assertThrows(NullPointerException.class, () -> builder.first("spring").second("tops").build());

		// then
		assertEquals("third word is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Words")
	@ParameterizedTest(name = "words: {arguments}")
	@ValueSource(strings = {"spring.tops.issued", "spring・tops・issued", "spring。tops。issued", "///spring・tops・issued"})
	public void words(String words) {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		ThreeWordAddress threeWordAddress = builder.words(words).build();

		// then
		assertAll("Three word address",
				() -> assertEquals("spring", threeWordAddress.getFirst(), "first"),
				() -> assertEquals("tops", threeWordAddress.getSecond(), "second"),
				() -> assertEquals("issued", threeWordAddress.getThird(), "third"));
	}

	@DisplayName("Builder")
	@Test
	public void builder() {
		// given
		ThreeWordAddress.ThreeWordAddressBuilder builder = ThreeWordAddress.builder();

		// when
		ThreeWordAddress threeWordAddress = builder.words("spring", "tops", "issued").build();

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
		ThreeWordAddress threeWordAddress = ThreeWordAddress.builder().words("spring", "tops", "issued").build();

		// when and then
		assertEquals("spring.tops.issued", threeWordAddress.toString());
	}

}