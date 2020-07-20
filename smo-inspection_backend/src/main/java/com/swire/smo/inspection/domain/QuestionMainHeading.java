
package com.swire.smo.inspection.domain;

import java.util.List;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class QuestionMainHeading {
	
	private Integer id;
	
	private String mainHeading;
	
	private boolean hasMorning;
	
	private boolean hasAfternoon;
	
	private List<QuestionSubHeading> questionSubHeading;
	
}
