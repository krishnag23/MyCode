package com.swire.smo.inspection.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Instantiates a new email DTO to set the parameters to send a mail.
 */
@Data
public class EmailDTO implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The to address.
	 */
	private String[] toAddress;

	/**
	 * The cc address.
	 */
	private String[] ccAddress;

	/**
	 * The bcc address.
	 */
	private String[] bccAddress;

	/**
	 * The sender id.
	 */
	private String senderId;

	/**
	 * The subject.
	 */
	private String subject;

	/**
	 * The body.
	 */
	private String body;

	/**
	 * The body type.
	 */
	private String bodyType;

	private List<File> attachments;
}
