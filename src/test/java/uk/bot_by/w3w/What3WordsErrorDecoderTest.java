package uk.bot_by.w3w;

import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.object.IsCompatibleType.typeCompatibleWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class What3WordsErrorDecoderTest {

	@Mock
	private Response.Body body;

	private What3WordsErrorDecoder decoder;

	private Response response;

	@BeforeEach
	void setUp() {
		decoder = new What3WordsErrorDecoder();
		response = Response.builder()
				.request(Request.create(Request.HttpMethod.GET, "/api", Collections.emptyMap(), null, UTF_8, null))
				.status(400)
				.reason("Bad Request")
				.headers(Collections.emptyMap())
				.body(body)
				.build();
	}

	@DisplayName("Response without body")
	@Test
	public void bodyIsNull() {
		// given
		body = null;
		response = Response.builder()
				.request(Request.create(Request.HttpMethod.GET, "/api", Collections.emptyMap(), null, UTF_8, null))
				.status(500)
				.reason("Test Error Response")
				.headers(Collections.emptyMap())
				.body(body)
				.build();

		// when
		Exception exception = decoder.decode("qwerty", response);

		// then
		assertAll("Response has no body",
				() -> assertThat("class", exception.getClass(), typeCompatibleWith(FeignException.class)),
				() -> assertEquals("[500 Test Error Response] during [GET] to [/api] [qwerty]: []", exception.getMessage(), "message"));
	}

	@DisplayName("Response body is not JSON")
	@Test
	public void unknownResponse() throws IOException {
		// given
		String bodyText = "xyz";
		when(body.asInputStream()).thenReturn(new ByteArrayInputStream(bodyText.getBytes())); // internal of Feign
		when(body.asReader(isA(Charset.class))).thenReturn(new StringReader(bodyText)); // error decoder

		// when
		Exception exception = decoder.decode("qwerty", response);

		// then
		assertAll("Response body is not JSON",
				() -> assertThat("class", exception.getClass(), typeCompatibleWith(FeignException.class)),
				() -> assertEquals("[400 Bad Request] during [GET] to [/api] [qwerty]: [xyz]", exception.getMessage(), "Exception message"));
	}

	@DisplayName("Response body is not error")
	@Test
	public void responseIsNotError() throws IOException {
		// given
		String bodyText = "{\"warning\":\"xyz\"}";
		when(body.asInputStream()).thenReturn(new ByteArrayInputStream(bodyText.getBytes())); // internal of Feign
		when(body.asReader(isA(Charset.class))).thenReturn(new StringReader(bodyText)); // error decoder

		// when
		Exception exception = decoder.decode("qwerty", response);

		// then
		assertAll("Response body is not error",
				() -> assertThat("class", exception.getClass(), typeCompatibleWith(FeignException.class)),
				() -> assertEquals("[400 Bad Request] during [GET] to [/api] [qwerty]: [{\"warning\":\"xyz\"}]", exception.getMessage(), "message"));
	}

	@DisplayName("Decode what3words API error")
	@Test
	public void decode() throws IOException {
		// given
		String bodyText = "{\"error\":{\"code\":\"Test\",\"message\":\"test error\"}}";
		when(body.asReader(isA(Charset.class))).thenReturn(new StringReader(bodyText));

		// when
		Exception exception = decoder.decode("qwerty", response);

		// then
		assertAll("Response body is not error",
				() -> assertThat("class", exception.getClass(), typeCompatibleWith(What3WordsException.class)),
				() -> assertEquals("test error", exception.getMessage(), "message"),
				() -> assertEquals("Test", ((What3WordsException) exception).getCode(), "code"));
	}
}