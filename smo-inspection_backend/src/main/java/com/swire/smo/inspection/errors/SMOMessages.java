
package com.swire.smo.inspection.errors;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Krishna
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SMOMessages implements Serializable {
	
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The message code.
	 */
	private int messageCode;

	/**
	 * The message.
	 */
	private String message;
	
}
