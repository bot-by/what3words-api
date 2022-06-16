package uk.bot_by.w3w;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
class KeyInterceptorTest {

  private KeyInterceptor interceptor;
  private RequestTemplate requestTemplate;

  @BeforeEach
  void setUp() {
    interceptor = new KeyInterceptor("qwerty");
    requestTemplate = new RequestTemplate();
  }

  @DisplayName("Add the API key to a request template")
  @Test
  void happyPath() {
    // when
    interceptor.apply(requestTemplate);

    // then
    assertAll("HTTP header with API key",
        () -> assertThat("HTTP header with API key", requestTemplate.headers(),
            hasKey("X-Api-Key")),
        () -> assertEquals("qwerty", requestTemplate.headers().get("X-Api-Key").iterator().next(),
            "The API key was added to the request template"));
  }

  @DisplayName("The API key header exists")
  @Test
  void headerExists() {
    // given
    requestTemplate.header("X-Api-Key", "abc");

    // when
    interceptor.apply(requestTemplate);

    // then
    assertAll("HTTP header with API key",
        () -> assertThat("HTTP header with API key", requestTemplate.headers(),
            hasKey("X-Api-Key")),
        () -> assertEquals("abc", requestTemplate.headers().get("X-Api-Key").iterator().next(),
            "The API key wasn't overwritten"));
  }

  @DisplayName("The API key query parameter exists")
  @Test
  void queryParameterExists() {
    // given
    requestTemplate.query("key", "xyz");

    // when
    interceptor.apply(requestTemplate);

    // then
    assertAll("HTTP header with API key",
        () -> assertThat("HTTP header with API key", requestTemplate.headers(),
            not(hasKey("X-Api-Key"))));
  }

}
