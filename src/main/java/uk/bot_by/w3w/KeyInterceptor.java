package uk.bot_by.w3w;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.jetbrains.annotations.NotNull;

/**
 * The key interceptor is used to set up the What3Words API key in common way.
 * <p>
 * The interceptor adds the HTTP header {@code X-Api-Key} to each request.
 * But if the {@linkplain RequestTemplate request template} contains another header {@code X-Api-Key}
 * or request parameter {@code key} the interceptor does nothing.
 */
public class KeyInterceptor implements RequestInterceptor {

	private final String key;

	public KeyInterceptor(@NotNull String key) {
		this.key = key;
	}

	@Override
	public void apply(RequestTemplate template) {
		if (!(template.headers().containsKey("X-Api-Key") || template.queries().containsKey("key"))) {
			template.header("X-Api-Key", key);
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{ key hash=" + key.hashCode() + " }";
	}

}
