package uk.bot_by.w3w;

public class What3WordsException extends RuntimeException {

	private final String code;
	private final int status;

	public What3WordsException(int status, String code, String message) {
		super(message);
		this.code = code;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public int getStatus() {
		return status;
	}

}
