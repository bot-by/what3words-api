/*
 * Copyright 2021,2022 Witalij Berdinskich
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

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.jetbrains.annotations.NotNull;

/**
 * The key interceptor is used to set up the <em>what3words</em> API key in common way.
 * <p>
 * The interceptor adds the HTTP header {@code X-Api-Key} to each request. But if the
 * {@linkplain RequestTemplate request template} contains another header {@code X-Api-Key} or
 * request parameter {@code key} the interceptor does nothing.
 *
 * @since 1.0.0
 */
public class KeyInterceptor implements RequestInterceptor {

  private final String key;

  public KeyInterceptor(@NotNull String key) {
    this.key = key;
  }

  /**
   * Add <em>what3words</em> API key to a request template.
   * <p>
   * If a request template has the header {@code X-Api-Key} or query parameter {@code key} it does
   * nothing.
   *
   * @param template a request template
   */
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
