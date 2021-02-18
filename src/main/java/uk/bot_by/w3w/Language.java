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

import java.util.Objects;

/**
 * Language object represents language entity that returns <em>what3words</em> API.
 * <p>
 * The implementations should implements {@link Object#toString() toString()} that returns language code.
 *
 * @see What3Words#availableLanguages()
 * @since 1.0.0
 */
public interface Language {

	static LanguageBuilder builder() {
		return new LanguageBuilder();
	}

	/**
	 * Two letter language code.
	 *
	 * @return 2 letter code
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes">List of ISO 639-1 codes</a>
	 */
	String getCode();

	/**
	 * English language name.
	 *
	 * @return language name
	 */
	String getName();

	/**
	 * Native language name.
	 *
	 * @return language name
	 */
	String getNativeName();

	/**
	 * Basic implementation of {@link Language}
	 *
	 * @since 1.0.0
	 */
	class BasicLanguage implements Language {

		private final String code;
		private final String name;
		private final String nativeName;

		private BasicLanguage(LanguageBuilder builder) {
			code = builder.code;
			name = builder.name;
			nativeName = builder.nativeName;
		}

		@Override
		public String getCode() {
			return code;
		}

		@Override
		public String getName() {
			return (null == name) ? code : name;
		}

		@Override
		public String getNativeName() {
			return (null == nativeName) ? code : nativeName;
		}

		/**
		 * Returns language code.
		 *
		 * @return language code
		 */
		@Override
		public String toString() {
			return code;
		}

		@Override
		public boolean equals(Object another) {
			if (this == another) return true;
			if (another == null || !Language.class.isAssignableFrom(another.getClass())) return false;

			return code.equals(((Language) another).getCode());
		}

		@Override
		public int hashCode() {
			return Objects.hash(code);
		}

	}

	/**
	 * Helper to constraint a language object.
	 *
	 * @since 1.0.0
	 */
	class LanguageBuilder {

		private String code;
		private String name;
		private String nativeName;

		private LanguageBuilder() {
		}

		/**
		 * Get a language object.
		 * <p>
		 * It checks that language code is not null.
		 *
		 * @return language
		 * @throws NullPointerException if language code is null
		 */
		public Language build() throws NullPointerException {
			Objects.requireNonNull(code, "language code is null");
			return new BasicLanguage(this);
		}

		/**
		 * Set language code.
		 *
		 * @param code language code
		 * @return the builder
		 */
		public LanguageBuilder code(@NotNull String code) {
			this.code = code;
			return this;
		}

		/**
		 * Set language name.
		 *
		 * @param name English language name
		 * @return the builder
		 */
		public LanguageBuilder name(@NotNull String name) {
			this.name = name;
			return this;
		}

		/**
		 * Set native language name.
		 *
		 * @param nativeName native language name
		 * @return the builder
		 */
		public LanguageBuilder nativeName(@NotNull String nativeName) {
			this.nativeName = nativeName;
			return this;
		}

	}

}
