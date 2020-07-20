package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class MainHeadingMapper implements RowMapper<MainHeading> {

	@Override
	public MainHeading mapRow(ResultSet rs, int rowNum) throws SQLException {
		MainHeading mainHeading = new MainHeading();
		mainHeading.setId(rs.getInt("id"));
		mainHeading.setMainHeading(rs.getString("main_heading"));
		mainHeading.setHasMorningMainHeadin(rs.getBoolean("morning_main_heading"));
		mainHeading.setHasAfternoonMainHeadin(rs.getBoolean("afternoon_main_heading"));

		return mainHeading;
	}

}
