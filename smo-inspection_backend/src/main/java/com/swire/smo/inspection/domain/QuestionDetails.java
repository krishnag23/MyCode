package com.swire.smo.inspection.domain;

import java.util.List;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class QuestionDetails {
	
    private Integer id;
	
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
	
	private List<AnswerDetails> answerDetaiils;

}
