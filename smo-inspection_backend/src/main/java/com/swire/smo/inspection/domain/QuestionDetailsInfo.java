
package com.swire.smo.inspection.domain;

import java.util.List;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class QuestionDetailsInfo {
	
	private List<QuestionMainHeading> questionMainHeading;
	
	private List<MasterUser> masterUsers;
	
}
