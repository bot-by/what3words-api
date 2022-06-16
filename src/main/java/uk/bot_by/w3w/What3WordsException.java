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

/**
 * Represent error response of <em>what3words</em> API.
 * <p>
 * Example of error response:
 * <pre><code class="language-json">
 * {
 *     "error": {
 *         "code": "BadWords",
 *         "message": "Invalid or non-existent 3 word address"
 *     }
 * }
 * </code></pre>
 *
 * @see <a
 * href="https://developer.what3words.com/public-api/docs#error-handling"><em>what3words</em>: Error
 * handling</a>
 * @since 1.0.0
 */
public class What3WordsException extends RuntimeException {

  private final String code;
  private final int status;

  public What3WordsException(int status, String code, String message) {
    super(message);
    this.code = code;
    this.status = status;
  }

  /**
   * Error code like <em>InvalidKey</em>, <em>DuplicateParameter</em> etc.
   *
   * @return error code
   */
  public String getCode() {
    return code;
  }

  /**
   * Error status like {@code 400}, {@code 401}, {@code 404} and so on.
   *
   * @return error status
   */
  public int getStatus() {
    return status;
  }

}
