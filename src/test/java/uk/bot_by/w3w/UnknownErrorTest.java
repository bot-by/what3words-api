package uk.bot_by.w3w;

import feign.Feign;
import feign.http2client.Http2Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class UnknownErrorTest {

	private static What3Words api;

	@BeforeAll
	public static void setUpClass() {
		api = Feign.builder()
				.client(new Http2Client())
				.decoder(new What3WordsDecoder())
				.errorDecoder(new What3WordsErrorDecoder())
				.requestInterceptor(new KeyInterceptor("abc-api-key"))
				.target(What3Words.class, "http://localhost:9876");
	}

	@DisplayName("Not found")
	@Test
	public void notFound() {
		// when
		Collection<Language> languages = api.availableLanguages("qwerty");
	}

}
