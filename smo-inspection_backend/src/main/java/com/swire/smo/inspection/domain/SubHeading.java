
package com.swire.smo.inspection.domain;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class SubHeading {
	
	private Integer id;

	private String subHeading;

	private Integer mainHeadingId;

	private boolean hasMorningSubHeading;

	private boolean hasAfternoonSubHeading;
	
}
