
package com.swire.smo.inspection.domain;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class QuestionAnswerJoinForEdit {
	
	private Integer responseid;
	
	private Integer QuestionId;

	private String reply;
	
	private String unsafeAactAction;

	private String actionRequired;

	private String personInCharge;

	private String targetCompletionDate;

	private String statusOfAction;

	private String dateOfActionCompleted;
	
	private String userId;
	
	private String reportingDate;
	
	private String level;

	private boolean IsIssueResolved;

	private Integer mainHeadingId;

	private Integer subHeadingId;

	private String question;

	private boolean hasMorning;

	private boolean hasafternoon;

	private String questionDesc;
	
	private String[] replyOption;	
	
}
