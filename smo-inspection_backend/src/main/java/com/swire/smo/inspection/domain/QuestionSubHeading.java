package com.swire.smo.inspection.domain;

import java.util.List;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class QuestionSubHeading {

	private Integer id;

	private String subHeading;

	private Integer mainHeadingId;

	private boolean hasMorning;

	private boolean hasAfternoon;

	private List<QuestionMaster> questionMaster;

}
