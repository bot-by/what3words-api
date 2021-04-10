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

import java.util.List;

/**
 * Strict holder of query parameters for the coordinates request {@link What3Words#convertToCoordinates(String, CoordinatesRequest)}.
 *
 * @since 1.0.0
 */
public class CoordinatesRequest {

	private final Words words;

	private CoordinatesRequest(CoordinatesRequestBuilder builder) {
		words = builder.words;
	}

	/**
	 * Get a builder to constraint coordinates query parameters.
	 *
	 * @return a builder
	 */
	public static CoordinatesRequestBuilder builder() {
		return new CoordinatesRequestBuilder();
	}

	/**
	 * Get a <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
	 *
	 * @return 3 word address
	 */
	public Words getWords() {
		return words;
	}

	/**
	 * Helper to constraint coordinates query parameters.
	 *
	 * @since 1.0.0
	 */
	public static class CoordinatesRequestBuilder {

		private Words words;

		private CoordinatesRequestBuilder() {
		}

		/**
		 * Build coordinates query parameters.
		 *
		 * @return coordinates query parameters
		 */
		public CoordinatesRequest build() {
			return new CoordinatesRequest(this);
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
		 *
		 * @param words 3 word address
		 * @return the builder
		 */
		public CoordinatesRequestBuilder words(@NotNull Words words) {
			this.words = words;
			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string.
		 *
		 * @param words 3 word address
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 * @see Words.WordsBuilder#words(String)
		 */
		public CoordinatesRequestBuilder words(@NotNull String words) throws IllegalArgumentException {
			this.words = Words.builder()
			                  .words(words)
			                  .build();
			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string array.
		 *
		 * @param words array with 3 elements
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 * @see Words.WordsBuilder#words(String...)
		 */
		public CoordinatesRequestBuilder words(@NotNull String... words) throws IllegalArgumentException {
			this.words = Words.builder()
			                  .words(words)
			                  .build();
			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string list.
		 *
		 * @param words list with 3 elements
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 * @see Words.WordsBuilder#words(List)
		 */
		public CoordinatesRequestBuilder words(@NotNull List<String> words) throws IllegalArgumentException {
			this.words = Words.builder()
			                  .words(words)
			                  .build();
			return this;
		}

	}

}
