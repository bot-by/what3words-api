package uk.bot_by.w3w;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("fast")
class LanguageTest {

	@DisplayName("Language code is missing")
	@Test
	public void missedLanguageCode() {
		// when
		Exception exception = assertThrows(NullPointerException.class, () -> Language.builder().name("Name").nativeName("Native name").build());

		// then
		assertEquals("language code is null", exception.getMessage(), "exception message");
	}

	@DisplayName("Language name and native name are null")
	@Test
	public void nameAndNativeNameAreNull() {
		// when
		Language language = Language.builder()
				.code("aa")
				.build();

		// then
		assertAll("Name and native name are null",
				() -> assertEquals("aa", language.getCode(), "code"),
				() -> assertEquals("aa", language.getName(), "name"),
				() -> assertEquals("aa", language.getNativeName(), "native name"));
	}

	@DisplayName("Builder")
	@Test
	public void builder() {
		// when
		Language language = Language.builder()
				.code("aa")
				.name("Name")
				.nativeName("Native name")
				.build();

		// then
		assertAll("Language",
				() -> assertEquals("aa", language.getCode(), "code"),
				() -> assertEquals("Name", language.getName(), "name"),
				() -> assertEquals("Native name", language.getNativeName(), "native name"));
	}

	@DisplayName("To string")
	@Test
	public void testToString() {
		// given
		Language language = Language.builder()
				.code("aa")
				.name("Name")
				.nativeName("Native name")
				.build();

		// when and then
		assertEquals("aa", language.toString());
	}

}