package com.swire.smo.inspection.util;

/**
 *
 * @author Sony George : sony.george@swirecnco.com
 */
public final class Constants {

	private Constants() {
	}

	public static final String DB_KEY = "dbname";

	public static final String HOST = "host";

	public static final String DB_USERNAME = "username";

	public static final String DB_PASS_KEY = "password";

	public static final String DB_DRIVER = "driver";

	public static final String DB_DRIVER_NAME = "jdbc:mysql://";

	public static final String DB_CONNECTION_PORT = ":3306/";

	public static final String DB_UNICODE_ENABLED = "?useUnicode=true";
	
	public static final String MAIL_SENDER = "swirecommunicator@gmail.com";
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

	/**
	 * The Constant MAIL_SMTP_STARTTLS_ENABLE.
	 */
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

	/**
	 * The Constant MAIL_SMTP_HOST.
	 */
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";

	/**
	 * The Constant MAIL_SMTP_PORT.
	 */
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";

	/**
	 * The Constant MAIL_BODYTYPE.
	 */
	public static final String MAIL_BODYTYPE = "text/html; charset=utf-8";

}
