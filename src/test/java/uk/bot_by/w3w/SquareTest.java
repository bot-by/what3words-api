package uk.bot_by.w3w;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("fast")
public class SquareTest {

  private Coordinates northeast;
  private Coordinates southwest;

  @BeforeEach
  public void setUp() {
    northeast = Coordinates.builder().latitude(49.39d).longitude(-1.01d).build();
    southwest = Coordinates.builder().latitude(51.03d).longitude(1.09d).build();
  }

  @DisplayName("Builder")
  @Test
  public void builder() {
    // given
    Square.SquareBuilder builder = Square.builder();

    builder.northeast(northeast).southwest(southwest);

    // when
    Square square = assertDoesNotThrow(builder::build);

    // then
    assertAll("Square", () -> assertNotNull(square, "square was built"),
        () -> assertEquals(northeast, square.getNortheast(), "northeast"),
        () -> assertEquals(southwest, square.getSouthwest(), "southwest"));
  }

  @DisplayName("One corner is missing")
  @ParameterizedTest(name = "{arguments}")
  @ValueSource(strings = {"northeast is null", "southwest is null"})
  public void cornerIsMissing(String expectedMessage) {
    // given
    Square.SquareBuilder builder = Square.builder();

    if (expectedMessage.startsWith("northeast")) {
      builder.southwest(southwest);
    }
    if (expectedMessage.startsWith("southwest")) {
      builder.northeast(northeast);
    }

    // when
    Exception exception = assertThrows(NullPointerException.class, builder::build);

    // then
    assertEquals(expectedMessage, exception.getMessage(), "exception message");

  }

  @DisplayName("Northeast as double values")
  @Test
  public void northeastAsDoubleValues() {
    // given
    Square.SquareBuilder builder = Square.builder();

    builder.southwest(southwest);

    // when
    builder.northeast(49.39d, -1.01d);

    // then
    Square square = assertDoesNotThrow(builder::build);

    assertAll("Square", () -> assertNotNull(square, "square was built"),
        () -> assertEquals(northeast, square.getNortheast(), "northeast"),
        () -> assertEquals(southwest, square.getSouthwest(), "southwest"));
  }

  @DisplayName("Northeast as BigDecimal values")
  @Test
  public void northeastAsBigDecimalValues() {
    // given
    Square.SquareBuilder builder = Square.builder();

    builder.southwest(southwest);

    // when
    builder.northeast(BigDecimal.valueOf(49.39d), BigDecimal.valueOf(-1.01d));

    // then
    Square square = assertDoesNotThrow(builder::build);

    assertAll("Square", () -> assertNotNull(square, "square was built"),
        () -> assertEquals(northeast, square.getNortheast(), "northeast"),
        () -> assertEquals(southwest, square.getSouthwest(), "southwest"));
  }

  @DisplayName("Southwest as double values")
  @Test
  public void southwestAsDoubleValues() {
    // given
    Square.SquareBuilder builder = Square.builder();

    builder.northeast(northeast);

    // when
    builder.southwest(51.03d, 1.09d);

    // then
    Square square = assertDoesNotThrow(builder::build);

    assertAll("Square", () -> assertNotNull(square, "square was built"),
        () -> assertEquals(northeast, square.getNortheast(), "northeast"),
        () -> assertEquals(southwest, square.getSouthwest(), "southwest"));
  }

  @DisplayName("Southwest as BigDecimal values")
  @Test
  public void southwestAsBigDecimalValues() {
    // given
    Square.SquareBuilder builder = Square.builder();

    builder.northeast(northeast);

    // when
    builder.southwest(BigDecimal.valueOf(51.03d), BigDecimal.valueOf(1.09d));

    // then
    Square square = assertDoesNotThrow(builder::build);

    assertAll("Square", () -> assertNotNull(square, "square was built"),
        () -> assertEquals(northeast, square.getNortheast(), "northeast"),
        () -> assertEquals(southwest, square.getSouthwest(), "southwest"));
  }

  @DisplayName("To string")
  @Test
  public void string() {
    // given
    Square square = Square.builder().northeast(49.39d, -1.01d).southwest(51.03d, 1.09d).build();

    // when and then
    assertEquals("{northeast:49.39,-1.01;southwest:51.03,1.09}", square.toString());
  }

}
