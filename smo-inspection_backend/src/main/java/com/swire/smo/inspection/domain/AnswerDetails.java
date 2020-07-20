
package com.swire.smo.inspection.domain;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class AnswerDetails {
	
	private Integer id;
	
	private String reply;
	
	private String unsafeAction;
	
	private String actionRequired;
	
	private String personInCharge;
	
	private String targetCompletionDate;
	
	private String statusOfAction;
	
	private String dateOfActionCompleted;
	
}
