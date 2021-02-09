package uk.bot_by.w3w;

import feign.Feign;
import feign.http2client.Http2Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("slow")
public class What3WordsTest {

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

	@DisplayName("Available languages")
	@Test
	public void availableLanguages() {
		// given
		Language german = Language.builder().code("de").name("").nativeName("").build();
		Language norwegian = Language.builder().code("no").name("").nativeName("").build();

		// when
		Collection<Language> languages = api.availableLanguages();

		// then
		assertAll("Available languages",
				() -> assertNotNull(languages, "not null"),
				() -> assertThat("two languages", languages, hasSize(2)),
				() -> assertThat("german, norwegian", languages, containsInAnyOrder(german, norwegian)));
	}

	@DisplayName("Available languages with API key")
	@Test
	public void availableLanguagesWithKey() {
		// given
		Language arabic = Language.builder().code("ar").name("").nativeName("").build();
		Language greek = Language.builder().code("el").name("").nativeName("").build();
		Language turkish = Language.builder().code("tr").name("").nativeName("").build();

		// when
		Collection<Language> languages = api.availableLanguages("xyz-api-key");

		// then
		assertAll("Available languages",
				() -> assertNotNull(languages, "not null"),
				() -> assertThat("three languages", languages, hasSize(3)),
				() -> assertThat("arabic, greek, turkish", languages, containsInAnyOrder(arabic, greek, turkish)));
	}

	@DisplayName("Convert coordinates to a 3 word address")
	@Test
	public void convertToAddress() {
		// given
		Coordinates coordinates = Coordinates.builder().latitude(51.381051d).longitude(-2.359591d).build();
		Language language = Language.builder().code("al").name("Alien Language").nativeName("Alienese").build();
		Map<String, Object> queryParameters = new HashMap<>();

		queryParameters.put("coordinates", coordinates);

		// when
		ThreeWordAddress threeWordAddress = api.convertToAddress(queryParameters);

		// then
		assertAll("Available languages",
				() -> assertNotNull(threeWordAddress, "not null"),
				() -> assertEquals("spring.tops.issued", threeWordAddress.toString(), "3wa"));
	}

	@DisplayName("Convert coordinates to a 3 word address")
	@Test
	public void convertToAddressWithKey() {
		// given
		ThreeWordAddressRequest threeWordAddressRequest = ThreeWordAddressRequest.builder()
				.coordinates(51.521251d, -0.203586d)
				.language(Language.builder()
						.code("al")
						.name("Alien Language")
						.nativeName("Alienese")
						.build())
				.build();

		// when
		ThreeWordAddress threeWordAddress = api.convertToAddress("xyz-api-key", threeWordAddressRequest);

		// then
		assertAll("Available languages",
				() -> assertNotNull(threeWordAddress, "not null"),
				() -> assertEquals("filled.count.soap", threeWordAddress.toString(), "3wa"));
	}

	@DisplayName("Convert coordinates to a 3 word address")
	@Test
	public void convertToAddressWithLanguage() {
		// given
		Coordinates coordinates = Coordinates.builder().latitude(51.841621d).longitude(16.571912d).build();
		Language polish = Language.builder().code("pl").name("").nativeName("").build();
		Map<String, Object> queryParameters = new HashMap<>();

		queryParameters.put("coordinates", coordinates);
		queryParameters.put("language", polish);

		// when
		ThreeWordAddress threeWordAddress = api.convertToAddress(queryParameters);

		// then
		assertAll("Available languages",
				() -> assertNotNull(threeWordAddress, "not null"),
				() -> assertEquals("brodaty.anteny.kwota", threeWordAddress.toString(), "3wa"));
	}

	@DisplayName("Convert 3 word address to coordinates")
	@Test
	public void convertToCoordinates() {
		//given
		ThreeWordAddress threeWordAddress = ThreeWordAddress.builder().words("spring.tops.issued".split("\\.")).build();
		Map<String, Object> queryParameters = new HashMap<>();

		queryParameters.put("words", threeWordAddress);

		// when
		Coordinates coordinates = api.convertToCoordinates(queryParameters);

		// then
		assertAll("Coordinates",
				() -> assertNotNull(coordinates, "not null"),
				() -> assertEquals("51.381051,-2.359591", coordinates.toString(), "coordinates"));
	}

	@DisplayName("Convert 3 word address to coordinates")
	@Test
	public void convertToCoordinatesWithKey() {
		//given
		CoordinatesRequest coordinatesRequest = CoordinatesRequest.builder().words("filled.count.soap".split("\\.")).build();

		// when
		Coordinates coordinates = api.convertToCoordinates("xyz-api-key", coordinatesRequest);

		// then
		assertAll("Coordinates",
				() -> assertNotNull(coordinates, "not null"),
				() -> assertEquals("51.521251,-0.203586", coordinates.toString(), "coordinates"));
	}

}
