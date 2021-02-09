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

import feign.Response;
import feign.codec.ErrorDecoder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.Reader;

import static feign.FeignException.errorStatus;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Decode error response of <em>what3words</em> API.
 *
 * @since 1.0.0
 */
public class What3WordsErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		if (null != response.body()) {
			try (Reader reader = response.body().asReader(UTF_8)) {
				JSONObject json = new JSONObject(new JSONTokener(reader));

				if (json.has("error")) {
					JSONObject error = json.getJSONObject("error");

					return new What3WordsException(response.status(), error.getString("code"), error.getString("message"));
				}
			} catch (JSONException exception) {
				// do nothing, it is not JSON, go to Feign's error processing
			} catch (IOException exception) {
				return exception;
			}
		}

		return errorStatus(methodKey, response);
	}

}
