package com.swire.smo.inspection;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swire.aws.SecretManager;

/**
 *
 * @author Sony George
 */
@Configuration
//@EnableTransactionManagement
public class SafetyAppConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SafetyAppConfig.class);

	@Autowired
	private Environment environment;

	@Autowired
	private ObjectMapper mapper;

	@Bean
	@ConfigurationProperties(prefix = "spring.safety.datasource")
	@Profile({"default"})
	public DataSource dataSource() {

		LOGGER.info("local DataSource from application.properties[spring.safety.datasource] initilised");
		return DataSourceBuilder.create().build();

	}

	@Bean
	@Profile({"dev", "stage", "prod"})
	public DataSource devDataSource() {

		LOGGER.info("#############################################");
		LOGGER.info("####### active Profile now is {} ##########", environment.getActiveProfiles());
		LOGGER.info("Requesting secretName from profile property file");
		String secretName = environment.getProperty("swire.safetyapp.db.credentials");
		LOGGER.info("secretName in profile ={}", secretName);
		LOGGER.info("#############################################");

		return getDataSourceBySecretName(secretName);

	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {

		return new JdbcTemplate(dataSource);

	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {

		return new NamedParameterJdbcTemplate(dataSource);

	}

	private DataSource getDataSourceBySecretName(String secretName) {

		try {

			String secret = SecretManager.getSecret(secretName);

			Map<String, String> map = mapper.readValue(secret, Map.class);

			return SMOInspectionApplication.buildDataSourceFromMap(map);

		} catch (Exception ex) {
			LOGGER.error("Exception while building datasource for SafetyAppConfig getDataSourceBySecretName, can not continue !!!, Secret Name is:" + secretName, ex);
			throw new RuntimeException(ex);
		}

	}

}
