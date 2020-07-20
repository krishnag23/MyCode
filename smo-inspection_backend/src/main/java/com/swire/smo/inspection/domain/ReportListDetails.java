package com.swire.smo.inspection.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class ReportListDetails implements Serializable {

	private Integer id;

	private String level;

	private boolean hasMorning;

	private boolean hasAfternoon;

	private String reportingDate;

	private String userId;

	private boolean isIssueInReport;
	
	private String responseIds;
	
	private String quesstionIds;

}
