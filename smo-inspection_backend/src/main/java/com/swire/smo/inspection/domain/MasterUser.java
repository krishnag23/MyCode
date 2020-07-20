
package com.swire.smo.inspection.domain;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class MasterUser  implements Serializable {
	
	private Integer id;
	
	private String userName;
	
	private String emaiId;
	
	
}
