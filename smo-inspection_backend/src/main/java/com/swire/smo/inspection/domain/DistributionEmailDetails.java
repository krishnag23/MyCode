
package com.swire.smo.inspection.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class DistributionEmailDetails implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The distribution id.
	 */
	private Integer distributionId;

	/**
	 * The primary mails.
	 */
	private String primaryMails;

	/**
	 * The secondary mails.
	 */
	private String secondaryMails;

	/**
	 * The consolidate mails.
	 */
	private String consolidateMails;

}
