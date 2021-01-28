package uk.bot_by.w3w;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class Coordinates {

	private final BigDecimal latitude;
	private final BigDecimal longitude;

	private Coordinates(CoordinatesBuilder builder) {
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
	}

	public static CoordinatesBuilder builder() {
		return new CoordinatesBuilder();
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return new StringJoiner(",")
				.add(latitude.toString())
				.add(longitude.toString())
				.toString();
	}

	public static class CoordinatesBuilder {

		private BigDecimal latitude;
		private BigDecimal longitude;

		private CoordinatesBuilder() {
			;
		}

		public Coordinates build() {
			Objects.requireNonNull(latitude, "latitude is null");
			Objects.requireNonNull(longitude, "longitude is null");
			return new Coordinates(this);
		}

		public CoordinatesBuilder latitude(double latitude) {
			return latitude(BigDecimal.valueOf(latitude));
		}

		public CoordinatesBuilder latitude(@NotNull BigDecimal latitude) {
			this.latitude = latitude;
			return this;
		}

		public CoordinatesBuilder longitude(double longitude) {
			return longitude(BigDecimal.valueOf(longitude));
		}

		public CoordinatesBuilder longitude(@NotNull BigDecimal longitude) {
			this.longitude = longitude;
			return this;
		}

	}

}
