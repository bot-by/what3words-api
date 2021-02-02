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
			if (first.isBlank()) {
				throw new IllegalArgumentException("empty first word");
			}
			this.first = first.strip();
			return this;
		}

		public ThreeWordAddressBuilder second(@NotNull String second) {
			if (second.isBlank()) {
				throw new IllegalArgumentException("empty second word");
			}
			this.second = second.strip();
			return this;
		}

		public ThreeWordAddressBuilder third(@NotNull String third) {
			if (third.isBlank()) {
				throw new IllegalArgumentException("empty third word");
			}
			this.third = third.strip();
			return this;
		}

		public ThreeWordAddressBuilder words(String... words) {
			if (3 > words.length) {
				throw new IllegalArgumentException("3 words is required");
			}

			first(words[0]);
			second(words[1]);
			third(words[2]);

			return this;
		}

		public ThreeWordAddressBuilder words(@NotNull List<String> words) {
			return words(words.toArray(new String[0]));
		}

	}

}
