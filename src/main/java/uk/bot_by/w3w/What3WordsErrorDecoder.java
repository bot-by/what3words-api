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

public class What3WordsErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
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

		return errorStatus(methodKey, response);
	}

}
