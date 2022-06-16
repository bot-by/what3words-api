/*
 * Copyright 2021,2022 Witalij Berdinskich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.bot_by.w3w;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;

/**
 * Square of coordinates has northeast and southwest corners.
 *
 * @since 1.0.0
 */
public interface Square {

  /**
   * Get a builder to constraint a square of coordinates.
   *
   * @return a builder
   */
  static SquareBuilder builder() {
    return new SquareBuilder();
  }

  /**
   * Get a northeast corner of square.
   *
   * @return northeast corner of square
   */
  Coordinates getNortheast();

  /**
   * Get a southwest corner of square.
   *
   * @return southwest corner of square
   */
  Coordinates getSouthwest();

  /**
   * Basic implementation of {@link Square}.
   *
   * @since 1.0.0
   */
  class BasicSquare implements Square {

    private final Coordinates northeast;
    private final Coordinates southwest;

    private BasicSquare(SquareBuilder builder) {
      northeast = builder.northeast;
      southwest = builder.southwest;
    }

    @Override
    public Coordinates getNortheast() {
      return northeast;
    }

    @Override
    public Coordinates getSouthwest() {
      return southwest;
    }

    /**
     * Returns a square of coordinates as semicolon-separated string of coordinates.
     *
     * @return semicolon-separated string of northeast and southwest of coordinates
     */
    @Override
    public String toString() {
      return new StringJoiner(";", "{", "}").add("northeast:" + northeast)
          .add("southwest:" + southwest).toString();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Square)) {
        return false;
      }

      Square that = (Square) o;

      if (!getNortheast().equals(that.getNortheast())) {
        return false;
      }
      return getSouthwest().equals(that.getSouthwest());
    }

    @Override
    public int hashCode() {
      int result = getNortheast().hashCode();
      result = 31 * result + getSouthwest().hashCode();
      return result;
    }

  }

  /**
   * Helper to constraint a square of coordinates.
   *
   * @since 1.0.0
   */
  class SquareBuilder {

    private Coordinates northeast;
    private Coordinates southwest;

    private SquareBuilder() {
    }

    /**
     * Get a square of coordinates.
     * <p>
     * It checks that northeast and southwest coordinates are not null.
     *
     * @return a square of coordinates
     * @throws NullPointerException if northeast or southwest are null
     */
    public Square build() throws NullPointerException {
      Objects.requireNonNull(northeast, "northeast is null");
      Objects.requireNonNull(southwest, "southwest is null");
      return new BasicSquare(this);
    }

    /**
     * Set northeast coordinates.
     *
     * @param northeast northeast
     * @return the builder
     */
    public SquareBuilder northeast(@NotNull Coordinates northeast) {
      this.northeast = northeast;
      return this;
    }

    /**
     * Set northeast coordinates.
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return the builder
     * @throws IllegalArgumentException if latitude or longitude are out of range
     */
    public SquareBuilder northeast(double latitude, double longitude)
        throws IllegalArgumentException {
      this.northeast = Coordinates.builder().latitude(latitude).longitude(longitude).build();
      return this;
    }

    /**
     * Set northeast coordinates.
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return the builder
     * @throws IllegalArgumentException if latitude or longitude are out of range
     */
    public SquareBuilder northeast(@NotNull BigDecimal latitude, @NotNull BigDecimal longitude)
        throws IllegalArgumentException {
      this.northeast = Coordinates.builder().latitude(latitude).longitude(longitude).build();
      return this;
    }

    /**
     * Set southwest coordinates.
     *
     * @param southwest southwest
     * @return the builder
     */
    public SquareBuilder southwest(@NotNull Coordinates southwest) {
      this.southwest = southwest;
      return this;
    }

    /**
     * Set southwest coordinates.
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return the builder
     * @throws IllegalArgumentException if latitude or longitude are out of range
     */
    public SquareBuilder southwest(double latitude, double longitude)
        throws IllegalArgumentException {
      this.southwest = Coordinates.builder().latitude(latitude).longitude(longitude).build();
      return this;
    }

    /**
     * Set southwest coordinates.
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return the builder
     * @throws IllegalArgumentException if latitude or longitude are out of range
     */
    public SquareBuilder southwest(@NotNull BigDecimal latitude, @NotNull BigDecimal longitude)
        throws IllegalArgumentException {
      this.southwest = Coordinates.builder().latitude(latitude).longitude(longitude).build();
      return this;
    }

  }

}
