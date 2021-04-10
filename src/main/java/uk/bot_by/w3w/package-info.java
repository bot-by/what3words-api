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

/**
 * Java wrapper for <em>what3words</em> API.
 * <p>
 * It is based on <em>what3words</em> API <strong>v3</strong>.
 * <h2>Usage</h2>
 * <p>Instantiate an instance of What3Words with <a href="https://github.com/OpenFeign/feign">Feign</a></p>
 * <pre><code class="language-java">
 * api = Feign.builder()
 *            .client(new Http2Client())
 *            .decoder(new What3WordsDecoder())
 *            .errorDecoder(new What3WordsErrorDecoder())
 *            .requestInterceptor(new KeyInterceptor("abc-api-key"))
 *            .target(What3Words.class, "https://api.what3words.com/");
 * </code></pre>
 * <p>This is a minimal configuration and KeyInterceptor is optional:
 * you can put an API key in request data.</p>
 * <h3>Convert coordinates to 3 word address</h3>
 * <pre><code class="language-java">
 * WordsRequest wordsRequest = WordsRequest.builder()
 *                                         .coordinates(51.381051d, -2.359591d)
 *                                         .language(Language.builder()
 *                                                           .code("uk")
 *                                                           .build())
 *                                         .build();
 *
 * Words words = api.convertToAddress(wordsRequest).getWords();
 * </code></pre>
 * <p>It converts coordinates of Roman Baths to
 * Ukrainian words <em>///зрання.поїздка.зрізаний</em>.</p>
 * <p>The language is optional: if you do not add it then
 * what3words returns English words <em>///spring.tops.issued</em>.</p>
 * <h3>Convert 3 word address to coordinates</h3>
 * <pre><code class="language-java">
 * CoordinatesRequest coordinatesRequest = CoordinatesRequest.builder()
 *                                                           .words("filled.count.soap")
 *                                                           .build();
 *
 * Coordinates coordinates = api.convertToCoordinates(coordinatesRequest).getCoordinates();
 * </code></pre>
 * <p>It returns
 * <a href="https://twitter.com/what3words/status/1005118966132551681">
 * well known coordinates</a> of the what3words's office.</p>
 * <h3>Get available languages</h3>
 * <pre><code class="language-java">
 * Collection&lt;Language&gt; languages = api.availableLanguages();
 * </code></pre>
 * <p>or with explicit API key</p>
 * <pre><code class="language-java">
 * Collection&lt;Language&gt; languages = api.availableLanguages("xyz-api-key");
 * </code></pre>
 *
 * @see <a href="https://developer.what3words.com/public-api/docs"><em>what3words</em>: API Reference Docs</a>
 * @since 1.0.0
 */
package uk.bot_by.w3w;