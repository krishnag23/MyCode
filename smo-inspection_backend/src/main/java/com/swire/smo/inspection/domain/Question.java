
package com.swire.smo.inspection.domain;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class Question {
	
	private Integer id;
	
	private String question;
	
	private Integer mainHeadingId;
	
	private Integer subHeadingId;
	
	private String description;
	
	private boolean hasMorning;
	
	private boolean hasAfternoon;
	
	private String[] replyOption;
	
}
