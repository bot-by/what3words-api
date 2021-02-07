package uk.bot_by.w3w;

import feign.Feign;
import feign.FeignException;
import feign.http2client.Http2Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("slow")
public class ErrorTest {

	private static What3Words api;

	@BeforeAll
	public static void setUpClass() {
		api = Feign.builder()
				.client(new Http2Client())
				.decoder(new What3WordsDecoder())
				.errorDecoder(new What3WordsErrorDecoder())
				.target(What3Words.class, "http://localhost:9876");
	}

	@DisplayName("Not found")
	@Test
	public void notFound() {
		// when
		assertThrows(FeignException.NotFound.class, () -> api.availableLanguages("qwerty"));
	}

	@DisplayName("Bad words")
	@Test
	public void badWords() {
		// when
		Exception exception = assertThrows(What3WordsException.class, () -> api.availableLanguages("bad-words"));

		// then
		assertEquals("Invalid or non-existent 3 word address", exception.getMessage(), "exception message");
	}

}