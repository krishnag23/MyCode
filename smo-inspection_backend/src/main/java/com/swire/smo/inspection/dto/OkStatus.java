package com.swire.smo.inspection.dto;

import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Krishna
 */
@Data
public class OkStatus {

	@NonNull
	private String message;
	@NonNull
	private String status;
}
