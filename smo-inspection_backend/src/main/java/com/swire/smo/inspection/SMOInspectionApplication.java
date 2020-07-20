package com.swire.smo.inspection;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import com.swire.smo.inspection.util.Constants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 *
 * @author Krishna
 */
@EnableSwagger2
@SpringBootApplication
public class SMOInspectionApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SMOInspectionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SMOInspectionApplication.class, args);
	}

	@Bean
	public Docket smo() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("smo").apiInfo(smoApiInfo()).select()
			.paths(PathSelectors.any()).build();
	}

	private ApiInfo smoApiInfo() {
		return new ApiInfoBuilder().title("SMO Inspection REST APIs").description("SMO Inspection REST APIs")
			.termsOfServiceUrl("https://www.swirecnco.com/").contact("SwireCNCO").license("SwireCNCO Licensed")
			.licenseUrl("https://www.swirecnco.com/").version("1.0").build();
	}

	public static DataSource buildDataSourceFromMap(Map<String, String> map) {

		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

		String host = map.get(Constants.HOST);
		String dbname = map.get(Constants.DB_KEY);

		String url = Constants.DB_DRIVER_NAME + host + Constants.DB_CONNECTION_PORT + dbname + Constants.DB_UNICODE_ENABLED;
		String username = map.get(Constants.DB_USERNAME);
		String password = map.get(Constants.DB_PASS_KEY);
		String driver = map.get(Constants.DB_DRIVER);

		LOGGER.info("jdbcUrl  in={}", url);
		LOGGER.info("driver   in={}", driver);
		LOGGER.info("username in={}", username);

		dataSourceBuilder.driverClassName(driver);
		dataSourceBuilder.password(password);
//		dataSourceBuilder.type(com.zaxxer.hikari.HikariDataSource.class);
		dataSourceBuilder.url(url);
		dataSourceBuilder.username(username);

		return dataSourceBuilder.build();
	}
}
