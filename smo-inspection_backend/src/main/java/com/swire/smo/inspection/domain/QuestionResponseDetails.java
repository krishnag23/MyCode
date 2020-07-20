package com.swire.smo.inspection.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class QuestionResponseDetails implements Serializable {

	private String level;

	private String userId;

	private boolean hasMorning;

	private boolean hasafternoon;

	private String reportingDate;
	
	private Integer isIssueInReport;
	
	private Integer unresolvedCases;
	
	private String updatedOn;
	
	private String dateOfList;

	private List<QuestionDetails> questionDetails;

}
