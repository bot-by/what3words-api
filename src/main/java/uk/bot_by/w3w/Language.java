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
