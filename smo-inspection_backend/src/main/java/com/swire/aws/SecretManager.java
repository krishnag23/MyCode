package com.swire.aws;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

/**
 *
 * @author Sony George
 */
public final class SecretManager {

	private SecretManager() {
	}

	/**
	 * for this method to work , both AWS_ACCESS_KEY and AWS_SECRET_KEY value
	 * should be set in the env.variable of the system
	 *
	 * @param secretKey
	 * @return value of the secretKey
	 */
	public static String getSecret(String secretKey) {

		String region = "ap-southeast-1";

		// Create a Secrets Manager client
		AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(region).build();

		// https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretKey);
		GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);

		client.shutdown();
		if (getSecretValueResult.getSecretString() != null) {
			return getSecretValueResult.getSecretString();
		} else {
			return new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array(), StandardCharsets.UTF_8);
		}

	}

}
