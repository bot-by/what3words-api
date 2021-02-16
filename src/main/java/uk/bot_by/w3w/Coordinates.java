/*
 * Copyright 2021 Witalij Berdinskich
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

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Coordinates has latitude and longitude values and should implements {@link Object#toString() toString()} that returns them as comma-separated
 * string of numbers with decimal point like <em>51.381051,-2.359591</em>.
 *
 * @since 1.0.0
 */
public interface Coordinates {

	/**
	 * Get a builder to constraint coordinates.
	 *
	 * @return a builder
	 */
	static CoordinatesBuilder builder() {
		return new CoordinatesBuilder();
	}

	/**
	 * Get latitude.
	 *
	 * @return latitude
	 */
	BigDecimal getLatitude();

	/**
	 * Get longitude.
	 *
	 * @return longitude
	 */
	BigDecimal getLongitude();

	/**
	 * Basic implementation of {@link Coordinates}.
	 *
	 * @since 1.0.0
	 */
	class BasicCoordinates implements Coordinates {

		private final BigDecimal latitude;
		private final BigDecimal longitude;

		private BasicCoordinates(CoordinatesBuilder builder) {
			latitude = builder.latitude;
			longitude = builder.longitude;
		}

		@Override
		public BigDecimal getLatitude() {
			return latitude;
		}

		@Override
		public BigDecimal getLongitude() {
			return longitude;
		}

		/**
		 * Returns coordinates as comma-separated string of numbers with decimal point.
		 *
		 * @return comma-separated string of latitude and longitude
		 */
		@Override
		public String toString() {
			return new StringJoiner(",")
					.add(latitude.toString())
					.add(longitude.toString())
					.toString();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Coordinates)) return false;

			Coordinates that = (Coordinates) o;

			if (!getLatitude().equals(that.getLatitude())) return false;
			return getLongitude().equals(that.getLongitude());
		}

		@Override
		public int hashCode() {
			int result = getLatitude().hashCode();
			result = 31 * result + getLongitude().hashCode();
			return result;
		}

	}

	/**
	 * Helper to constraint coordinates.
	 *
	 * @since 1.0.0
	 */
	class CoordinatesBuilder {

		private static final BigDecimal LATITUDE_MAXIMUM = BigDecimal.valueOf(90);
		private static final BigDecimal LATITUDE_MINIMUM = BigDecimal.valueOf(-90);
		private static final BigDecimal LONGITUDE_MAXIMUM = BigDecimal.valueOf(180);
		private static final BigDecimal LONGITUDE_MINIMUM = BigDecimal.valueOf(-180);

		private BigDecimal latitude;
		private BigDecimal longitude;

		private CoordinatesBuilder() {
		}

		/**
		 * Get coordinates.
		 * <p>
		 * It checks that latitude and longitude are not null.
		 *
		 * @return coordinates
		 * @throws NullPointerException if latitude or longitude are null
		 */
		public Coordinates build() throws NullPointerException {
			Objects.requireNonNull(latitude, "latitude is null");
			Objects.requireNonNull(longitude, "longitude is null");
			return new BasicCoordinates(this);
		}

		/**
		 * Set latitude.
		 * <p>
		 * Latitude must be in the range of -90 to 90 (inclusive).
		 *
		 * @param latitude latitude
		 * @return the builder
		 * @throws IllegalArgumentException if latitude is out of range
		 */
		public CoordinatesBuilder latitude(double latitude) throws IllegalArgumentException {
			return latitude(BigDecimal.valueOf(latitude));
		}

		/**
		 * Set latitude.
		 * <p>
		 * Latitude must be in the range of -90 to 90 (inclusive).
		 *
		 * @param latitude latitude
		 * @return the builder
		 * @throws IllegalArgumentException if latitude is out of range
		 */
		public CoordinatesBuilder latitude(@NotNull BigDecimal latitude) throws IllegalArgumentException {
			if (0 < LATITUDE_MINIMUM.compareTo(latitude) || 0 > LATITUDE_MAXIMUM.compareTo(latitude)) {
				throw new IllegalArgumentException("latitude must be in the range of -90 to 90");
			}
			this.latitude = latitude;
			return this;
		}

		/**
		 * Set longitude value.
		 * <p>
		 * Longitude must be in the range of -180 to 180 (inclusive).
		 *
		 * @param longitude longitude
		 * @return the builder
		 * @throws IllegalArgumentException if longitude is out of range
		 */
		public CoordinatesBuilder longitude(double longitude) throws IllegalArgumentException {
			return longitude(BigDecimal.valueOf(longitude));
		}

		/**
		 * Set longitude value.
		 * <p>
		 * Longitude must be in the range of -180 to 180 (inclusive).
		 *
		 * @param longitude longitude
		 * @return the builder
		 * @throws IllegalArgumentException if longitude is out of range
		 */
		public CoordinatesBuilder longitude(@NotNull BigDecimal longitude) throws IllegalArgumentException {
			if (0 < LONGITUDE_MINIMUM.compareTo(longitude) || 0 > LONGITUDE_MAXIMUM.compareTo(longitude)) {
				throw new IllegalArgumentException("longitude must be in the range of -180 to 180");
			}
			this.longitude = longitude;
			return this;
		}

	}

}
