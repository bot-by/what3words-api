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

public class Language {

	private final String code;
	private final String name;
	private final String nativeName;

	private Language(LanguageBuilder builder) {
		this.code = builder.code;
		this.name = builder.name;
		this.nativeName = builder.nativeName;
	}

	public static LanguageBuilder builder() {
		return new LanguageBuilder();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getNativeName() {
		return nativeName;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public boolean equals(Object another) {
		if (this == another) return true;
		if (another == null || getClass() != another.getClass()) return false;

		return code.equals(((Language) another).code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	public static class LanguageBuilder {

		private String code;
		private String name;
		private String nativeName;

		private LanguageBuilder() {
		}

		public Language build() {
			Objects.requireNonNull(code, "language code is null");
			Objects.requireNonNull(name, "language name is null");
			Objects.requireNonNull(nativeName, "language native name is null");
			return new Language(this);
		}

		public LanguageBuilder code(@NotNull String code) {
			this.code = code;
			return this;
		}

		public LanguageBuilder name(@NotNull String name) {
			this.name = name;
			return this;
		}

		public LanguageBuilder nativeName(@NotNull String nativeName) {
			this.nativeName = nativeName;
			return this;
		}

	}

}
