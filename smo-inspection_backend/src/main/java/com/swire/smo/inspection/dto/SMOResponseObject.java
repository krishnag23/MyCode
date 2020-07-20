package com.swire.smo.inspection.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class SMOResponseObject<T> implements Serializable {

	private ServerStatus status;//ERROR or SUCCESS
	private String statusMessage;
	private Map<String, String> errors;
	private T data;

	public void addError(String key, String errorMessage) {
		if (errors == null) {
			errors = new ConcurrentHashMap<String, String>(10);
		}
		errors.put(key, errorMessage);
	}

	public void setStatusAsErrorWithMessage(String errorMessage) {
		status = ServerStatus.ERROR;
		statusMessage = errorMessage;
	}

	public void setStatusAsSuccessWithMessage(String successMessage) {
		status = ServerStatus.SUCCESS;
		statusMessage = successMessage;
	}
}
