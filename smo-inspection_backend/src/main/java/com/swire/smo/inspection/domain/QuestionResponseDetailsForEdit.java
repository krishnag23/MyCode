package com.swire.smo.inspection.domain;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class QuestionResponseDetailsForEdit {

	private Integer id;
	
	private String level;

	private String userId;

	private boolean hasMorning;

	private boolean hasafternoon;

	private String reportingDate;

	private Integer QuestionId;

	private String reply;

	private String unsafeAactAction;

	private String actionRequired;

	private String personInCharge;

	private String targetCompletionDate;

	private String statusOfAction;

	private String dateOfActionCompleted;

	private boolean IsIssueResolved;

	private Integer mainHeadingId;

	private Integer subHeadingId;

}
