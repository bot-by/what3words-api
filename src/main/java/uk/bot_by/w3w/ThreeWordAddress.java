package uk.bot_by.w3w;

import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;

public class ThreeWordAddress {

	private final String first;
	private final String second;
	private final String third;

	public ThreeWordAddress(@NotNull String[] words) {
		this(words[0], words[1], words[2]);
	}

	public ThreeWordAddress(@NotNull String first, @NotNull String second, @NotNull String third) {
		this.first = first;
		this.second = second;
		this.third = third;
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

}
