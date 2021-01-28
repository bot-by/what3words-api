package uk.bot_by.w3w;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class ThreeWordAddress {

	private final String first;
	private final String second;
	private final String third;

	private ThreeWordAddress(ThreeWordAddressBuilder builder) {
		this.first = builder.first;
		this.second = builder.second;
		this.third = builder.third;
	}

	public static ThreeWordAddressBuilder builder() {
		return new ThreeWordAddressBuilder();
	}

	public String getFirst() {
		return first;
	}

	public String getSecond() {
		return second;
	}

	public String getThird() {
		return third;
	}

	@Override
	public String toString() {
		return new StringJoiner(".")
				.add(first)
				.add(second)
				.add(third)
				.toString();
	}

	public static class ThreeWordAddressBuilder {

		private String first;
		private String second;
		private String third;

		private ThreeWordAddressBuilder() {
		}

		public ThreeWordAddress build() {
			Objects.requireNonNull(first, "first word is null");
			Objects.requireNonNull(second, "second word is null");
			Objects.requireNonNull(third, "third word is null");
			return new ThreeWordAddress(this);
		}

		public ThreeWordAddressBuilder first(@NotNull String first) {
			this.first = first;
			return this;
		}

		public ThreeWordAddressBuilder second(@NotNull String second) {
			this.second = second;
			return this;
		}

		public ThreeWordAddressBuilder third(@NotNull String third) {
			this.third = third;
			return this;
		}

		public ThreeWordAddressBuilder words(@NotNull String[] words) {
			if (3 > words.length) {
				throw new IllegalArgumentException("three words is required");
			}

			first = words[0];
			second = words[1];
			third = words[2];

			return this;
		}

		public ThreeWordAddressBuilder words(@NotNull List<String> words) {
			return words(words.toArray(new String[0]));
		}

	}
}
