package uk.bot_by.w3w;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class KeyInterceptor implements RequestInterceptor {

	private final String key;

	public KeyInterceptor(@NotNull String key) {
		this.key = key;
	}

	@Override
	public void apply(RequestTemplate template) {
		template.headers().putIfAbsent("X-Api-Key", Collections.singleton(key));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{ key hash=" + key.hashCode() + " }";
	}

}
