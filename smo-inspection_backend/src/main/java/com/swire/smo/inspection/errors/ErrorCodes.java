package com.swire.smo.inspection.errors;

/**
 *
 * @author Krishna
 */
public enum ErrorCodes {
	/**
	 * The database connection failed.
	 */
	DATABASE_CONNECTION_FAILED(0, "A database connection is lost...."),
	/**
	 * The server error.
	 */
	SERVER_ERROR(1, "An internal Error occurred"),
	/**
	 * The success message.
	 */
	SUCCESS_MESSAGE(2, "SUCCESS"),
	/**
	 * The failure message.
	 */
	FAILURE_MESSAGE(3, "Unable to process request"),
	/**
	 * The invalid imo no.
	 */
	INVALID_IMO_NO(4, "Invalid IMO number"),
	/**
	 * The unauthorized imo no.
	 */
	UNAUTHORIZED_IMO_NO(5, "UnAuthorized IMO No"),
	/**
	 * The invalid fcm token.
	 */
	INVALID_FCM_TOKEN(6, "Invalid FCM Token"),
	REQUIRED_VALUE_MISSING(7, "Required Business Logic Value Missing"),

	EXISTING_LOGIN(8, "Another Device Logged into the Vessel you have requested"),

	INVALID_ID(9, "Invalid ID");

	/**
	 * The code.
	 */
	private final int code;

	/**
	 * The message.
	 */
	private final String message;

	/**
	 * Instantiates a new error codes.
	 *
	 * @param code the code
	 * @param description the description
	 */
	private ErrorCodes(int code, String description) {
		this.code = code;
		this.message = description;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "{'messageCode':" + code + ", " + "message:" + message + "}";
	}
}
