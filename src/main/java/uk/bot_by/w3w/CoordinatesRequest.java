package uk.bot_by.w3w;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoordinatesRequest {

	private final ThreeWordAddress words;

	private CoordinatesRequest(CoordinatesRequestBuilder builder) {
		words = builder.threeWordAddress;
	}

	public static CoordinatesRequestBuilder builder() {
		return new CoordinatesRequestBuilder();
	}

	public ThreeWordAddress getWords() {
		return words;
	}

	public static class CoordinatesRequestBuilder {

		private ThreeWordAddress threeWordAddress;

		private CoordinatesRequestBuilder() {
		}

		public CoordinatesRequest build() {
			return new CoordinatesRequest(this);
		}

		public CoordinatesRequestBuilder words(@NotNull ThreeWordAddress threeWordAddress) {
			this.threeWordAddress = threeWordAddress;
			return this;
		}

		public CoordinatesRequestBuilder words(String... words) {
			this.threeWordAddress = ThreeWordAddress.builder().words(words).build();
			return this;
		}

		public CoordinatesRequestBuilder words(@NotNull List<String> words) {
			this.threeWordAddress = ThreeWordAddress.builder().words(words).build();
			return this;
		}

	}

}
