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
import java.util.Objects;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * ThreeWordAddress contains three parts of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> and should implements {@link Object#toString() toString()} that returns them as
 * dot-separated string like <em>chest.elbowed.speaking</em>.
 *
 * @since 1.0.0
 */
public interface ThreeWordAddress {

	/**
	 * Get a builder to constraint <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
	 *
	 * @return a builder
	 */
	static ThreeWordAddressBuilder builder() {
		return new ThreeWordAddressBuilder();
	}

	/**
	 * Get first word.
	 *
	 * @return first word
	 */
	String getFirst();

	/**
	 * Get second word.
	 *
	 * @return second word
	 */
	String getSecond();

	/**
	 * Get third word.
	 *
	 * @return third word
	 */
	String getThird();

	/**
	 * Basic implementation of {@link ThreeWordAddress}
	 *
	 * @since 1.0.0
	 */
	class BasicThreeWordAddress implements ThreeWordAddress {

		private final String first;
		private final String second;
		private final String third;

		private BasicThreeWordAddress(ThreeWordAddressBuilder builder) {
			this.first = builder.first;
			this.second = builder.second;
			this.third = builder.third;
		}

		@Override
		public String getFirst() {
			return first;
		}

		@Override
		public String getSecond() {
			return second;
		}

		@Override
		public String getThird() {
			return third;
		}

		/**
		 * Returns <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> as dot-separated string
		 *
		 * @return dot-separated string
		 */
		@Override
		public String toString() {
			return new StringJoiner(".")
					.add(first)
					.add(second)
					.add(third)
					.toString();
		}

	}

	/**
	 * Helper to constraint <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
	 * <p>
	 * Each word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> must match the pattern:
	 * <pre><code class="language-regex">^[^0-9`~!@#$%^&amp;*()+\-_=\[{}\\|'&lt;,.&gt;?/&quot;;:£§º©®\s]{1,}$</code></pre>
	 *
	 * @since 1.0.0
	 */
	class ThreeWordAddressBuilder {

		private static final int FIRST_NON_SLASH = 3;
		private static final String THREE_LEADING_SLASHES = "///";
		private static final String WORD_ADDRESS_DELIMITER_PATTERN = "[・.。]";
		private static final Pattern WORD_ADDRESS_PATTERN = Pattern.compile("^[^0-9`~!@#$%^&*()+\\-_=\\[{}\\\\|'<,.>?/\";:£§º©®\\s]{1,}$");

		private String first;
		private String second;
		private String third;

		private ThreeWordAddressBuilder() {
		}

		/**
		 * Get <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>.
		 * <p>
		 * It checks that first, second and third words are not null.
		 *
		 * @return <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr>
		 * @throws NullPointerException if first, second or third words are null.
		 */
		public ThreeWordAddress build() throws NullPointerException {
			Objects.requireNonNull(first, "first word is null");
			Objects.requireNonNull(second, "second word is null");
			Objects.requireNonNull(third, "third word is null");
			return new BasicThreeWordAddress(this);
		}

		/**
		 * Set first word.
		 *
		 * @param word first word
		 * @return the builder
		 * @throws IllegalArgumentException if the word is blank or empty, if word does not match pattern
		 */
		public ThreeWordAddressBuilder first(@NotNull String word) throws IllegalArgumentException {
			isValidWord(word);
			first = word;
			return this;
		}

		/**
		 * Set second word.
		 *
		 * @param word second word
		 * @return the builder
		 * @throws IllegalArgumentException if the word is blank or empty, if word does not match pattern
		 */
		public ThreeWordAddressBuilder second(@NotNull String word) throws IllegalArgumentException {
			isValidWord(word);
			second = word;
			return this;
		}

		/**
		 * Set third word.
		 *
		 * @param word third word
		 * @return the builder
		 * @throws IllegalArgumentException if the word is blank or empty, if word does not match pattern
		 */
		public ThreeWordAddressBuilder third(@NotNull String word) throws IllegalArgumentException {
			isValidWord(word);
			third = word;
			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string.
		 * <p>
		 * The string must contain three words separated by point with optional leading three slashes:
		 * <ul>
		 *     <li>first.second.third</li>
		 *     <li>///first.second.third</li>
		 *     <li>first・second・third</li>
		 *     <li>first。second。third</li>
		 * </ul>
		 *
		 * @param words 3 word address
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 */
		public ThreeWordAddressBuilder words(@NotNull String words) throws IllegalArgumentException {
			if (words.isBlank()) {
				throw new IllegalArgumentException("empty words");
			}

			if (words.startsWith(THREE_LEADING_SLASHES)) {
				return words(words.substring(FIRST_NON_SLASH));
			}

			return words(words.split(WORD_ADDRESS_DELIMITER_PATTERN));
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string array.
		 *
		 * @param words array with 3 elements
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 */
		public ThreeWordAddressBuilder words(@NotNull String... words) throws IllegalArgumentException {
			if (3 > words.length) {
				throw new IllegalArgumentException("3 words are required");
			}

			first(words[0]);
			second(words[1]);
			third(words[2]);

			return this;
		}

		/**
		 * Set <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> by a string list.
		 *
		 * @param words list with 3 elements
		 * @return the builder
		 * @throws IllegalArgumentException if the words is blank or empty, if any word of <abbr class="tooltip">3wa<span class="tooltiptext">3 word address</span></abbr> does not match pattern
		 */
		public ThreeWordAddressBuilder words(@NotNull List<String> words) throws IllegalArgumentException {
			return words(words.toArray(new String[0]));
		}

		private void isValidWord(String word) {
			if (word.isBlank()) {
				throw new IllegalArgumentException("empty word");
			}
			if (!WORD_ADDRESS_PATTERN.matcher(word).matches()) {
				throw new IllegalArgumentException("bad word");
			}
		}

	}

}
