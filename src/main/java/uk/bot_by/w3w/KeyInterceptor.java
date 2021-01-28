package uk.bot_by.w3w;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.jetbrains.annotations.NotNull;

public class KeyInterceptor implements RequestInterceptor {

	private final String key;

	public KeyInterceptor(@NotNull String key) {
		this.key = key;
	}

	@Override
	public void apply(RequestTemplate template) {
		if (!template.headers().containsKey("X-Api-Key")) {
			template.header("X-Api-Key", key);
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{ key hash=" + key.hashCode() + " }";
	}

}
