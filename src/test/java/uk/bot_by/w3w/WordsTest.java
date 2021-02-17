package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("fast")
class WordsTest {

	@DisplayName("Word array is too short")
	@Test
	public void lengthOfArray() {
		// given
		String[] words = {"spring", "issued"};
		Words.WordsBuilder builder = Words.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words(words).build());

		// then
		assertEquals("3 words are required", exception.getMessage(), "exception message");
	}

	@DisplayName("Word list is too short")
	@Test
	public void sizeOfList() {
		// given
		List<String> words = Arrays.asList("spring", "issued");
		Words.WordsBuilder builder = Words.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words(words).build());

		// then
		assertEquals("3 words are required", exception.getMessage(), "exception message");
	}

	@DisplayName("Bad word")
	@ParameterizedTest(name = "words: {arguments}")
	@CsvSource({"firs+,second,third",
			"first,$econd,third",
			"first,second,th|rd"})
	public void badWord(String first, String second, String third) {
		// given
		Words.WordsBuilder builder = Words.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.first(first).second(second).third(third));

		// then
		assertEquals("bad word", exception.getMessage(), "exception message");
	}

	@DisplayName("Bad words")
	@Test
	public void badWords() {
		// given
		Words.WordsBuilder builder = Words.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words("spring・top$・issued"));

		// then
		assertEquals("bad word", exception.getMessage(), "exception message");
	}

	@DisplayName("Word is blank or empty")
	@ParameterizedTest(name = "words: \"{arguments}\"")
	@CsvSource({"' ',tops,issued", "'',tops,issued",
			"spring,' ',issued", "spring,'',issued",
			"spring,tops,' '", "spring,tops,''"})
	public void emptyWord(String first, String second, String third) {
		// given
		Words.WordsBuilder builder = Words.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.first(first).second(second).third(third).build());

		// then
		assertEquals("empty word", exception.getMessage(), "exception message");
	}

	@DisplayName("Words are blank or empty")
	@ParameterizedTest(name = "words: \"{arguments}\"")
	@EmptySource
	@ValueSource(strings = " ")
	public void emptyWords(String words) {
		// given
		Words.WordsBuilder builder = Words.builder();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.words(words));

		// then
		assertEquals("empty words", exception.getMessage(), "exception message");
	}

	@DisplayName("Word is missing")
	@ParameterizedTest(name = "words: \"{arguments}\"")
	@CsvSource({",tops,issued,first word is null", "spring,,issued,second word is null", "spring,tops,,third word is null"})
	public void wordIsMissing(String first, String second, String third, String expectedMessage) {
		// given
		Words.WordsBuilder builder = Words.builder();
		Optional.ofNullable(first).ifPresent(builder::first);
		Optional.ofNullable(second).ifPresent(builder::second);
		Optional.ofNullable(third).ifPresent(builder::third);

		// when
		Exception exception = assertThrows(NullPointerException.class, builder::build);

		// then
		assertEquals(expectedMessage, exception.getMessage(), "exception message");
	}

	@DisplayName("Words as array")
	@Test
	public void wordsAsArray() {
		// given
		Words.WordsBuilder builder = Words.builder();

		// when
		Words actualWords = builder.words("spring", "tops", "issued").build();

		// then
		assertAll("Three word address",
				() -> assertEquals("spring", actualWords.getFirst(), "first"),
				() -> assertEquals("tops", actualWords.getSecond(), "second"),
				() -> assertEquals("issued", actualWords.getThird(), "third"));
	}

	@DisplayName("Words as list")
	@Test
	public void wordsAsList() {
		// given
		Words.WordsBuilder builder = Words.builder();

		// when
		Words actualWords = builder.words(Arrays.asList("spring", "tops", "issued")).build();

		// then
		assertAll("Three word address",
				() -> assertEquals("spring", actualWords.getFirst(), "first"),
				() -> assertEquals("tops", actualWords.getSecond(), "second"),
				() -> assertEquals("issued", actualWords.getThird(), "third"));
	}

	@DisplayName("Words as string")
	@ParameterizedTest(name = "words: {arguments}")
	@ValueSource(strings = {"spring.tops.issued", "spring・tops・issued", "spring。tops。issued", "///spring・tops・issued"})
	public void wordsAsString(String words) {
		// given
		Words.WordsBuilder builder = Words.builder();

		// when
		Words actualWords = builder.words(words).build();

		// then
		assertAll("Three word address",
				() -> assertEquals("spring", actualWords.getFirst(), "first"),
				() -> assertEquals("tops", actualWords.getSecond(), "second"),
				() -> assertEquals("issued", actualWords.getThird(), "third"));
	}

	@DisplayName("Builder")
	@Test
	public void builder() {
		// given
		Words.WordsBuilder builder = Words.builder();

		// when
		Words words = builder.words("spring", "tops", "issued").build();

		// then
		assertAll("Three word address",
				() -> assertEquals("spring", words.getFirst(), "first"),
				() -> assertEquals("tops", words.getSecond(), "second"),
				() -> assertEquals("issued", words.getThird(), "third"));
	}

	@DisplayName("To string")
	@Test
	public void testToString() {
		// given
		Words words = Words.builder().words("spring", "tops", "issued").build();

		// when and then
		assertEquals("spring.tops.issued", words.toString());
	}

}