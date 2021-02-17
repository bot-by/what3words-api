package uk.bot_by.w3w;

import feign.Request;
import feign.Response;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class What3WordsDecoderTest {

	@Mock
	private Response.Body body;

	private What3WordsDecoder decoder;
	private Response response;

	@BeforeEach
	void setUp() {
		decoder = new What3WordsDecoder();
		response = Response.builder()
				.request(Request.create(Request.HttpMethod.GET, "/api", Collections.emptyMap(), null, UTF_8, null))
				.status(200)
				.reason("OK")
				.headers(Collections.emptyMap())
				.body(body)
				.build();
	}

	@DisplayName("Response without body")
	@ParameterizedTest(name = "{arguments}")
	@ValueSource(classes = {Collection.class, Coordinates.class, Words.class})
	@SuppressWarnings("rawtypes")
	public void bodyIsNull(Class type) {
		// given
		body = null;
		response = Response.builder()
				.request(Request.create(Request.HttpMethod.GET, "/api", Collections.emptyMap(), null, UTF_8, null))
				.status(200)
				.reason("OK")
				.headers(Collections.emptyMap())
				.body(body)
				.build();

		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> decoder.decode(response, type));

		// then
		assertEquals("Empty body", exception.getMessage(), "Exception message");
	}

	@DisplayName("Type is not supported")
	@Test
	public void typeIsNotSupported() throws IOException {
		// given
		when(body.asReader(isA(Charset.class))).thenReturn(new StringReader("{ \"a\": \"b\" }"));

		// when
		Exception exception = assertThrows(UnsupportedOperationException.class, () -> decoder.decode(response, String.class));

		// then
		assertEquals("Type class java.lang.String is not supported", exception.getMessage(), "Exception message");
	}

	@DisplayName("Response body is not JSON")
	@Test
	public void unknownResponse() throws IOException {
		// given
		when(body.asReader(isA(Charset.class))).thenReturn(new StringReader("qwerty"));

		// when
		assertThrows(JSONException.class, () -> decoder.decode(response, String.class));
	}

	@DisplayName("Available languages")
	@Test
	@SuppressWarnings("unchecked")
	public void availableLanguages() throws IOException, NoSuchMethodException {
		// given
		Type languageCollection = What3Words.class.getMethod("availableLanguages").getGenericReturnType();
		Language expectedLanguage = Language.builder()
				.code("aa")
				.name("")
				.nativeName("")
				.build();
		when(body.asReader(isA(Charset.class))).thenReturn(new StringReader("{ \"languages\": [ { \"code\": \"aa\", \"name\": \"Name\", " +
				"\"nativeName\": \"Native name\" } ] }"));

		// when
		Collection<Language> availableLanguages = (Collection<Language>) decoder.decode(response, languageCollection);

		// then
		assertAll("Available languages",
				() -> assertNotNull(availableLanguages, "not null"),
				() -> assertThat("one item", availableLanguages, hasSize(1)),
				() -> assertThat("language code is equal", availableLanguages, contains(expectedLanguage)));
	}

	@DisplayName("Coordinates")
	@Test
	public void coordinates() throws IOException {
		// given
		when(body.asReader(isA(Charset.class))).thenReturn(new StringReader("{ \"coordinates\": { \"lng\": -2.359591, \"lat\": 51.381051 } }"));

		// when
		Coordinates coordinates = (Coordinates) decoder.decode(response, Coordinates.class);

		// then
		assertAll("Coordinates",
				() -> assertNotNull(coordinates, "not null"),
				() -> assertEquals(BigDecimal.valueOf(-2.359591d), coordinates.getLongitude(), "longitude"),
				() -> assertEquals(BigDecimal.valueOf(51.381051d), coordinates.getLatitude(), "latitude"));
	}

	@DisplayName("Three word address")
	@Test
	public void words() throws IOException {
		// given
		when(body.asReader(isA(Charset.class))).thenReturn(new StringReader("{ \"words\": \"spring.tops.issued\" }"));

		// when
		Words words = (Words) decoder.decode(response, Words.class);

		// then
		assertAll("Three word address",
				() -> assertNotNull(words, "not null"),
				() -> assertEquals("spring", words.getFirst(), "first"),
				() -> assertEquals("tops", words.getSecond(), "second"),
				() -> assertEquals("issued", words.getThird(), "third"));
	}

}