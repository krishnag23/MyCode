package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class SubHeadingMapper implements RowMapper<SubHeading> {

	@Override
	public SubHeading mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubHeading subHeading = new SubHeading();
		subHeading.setId(rs.getInt("id"));
		subHeading.setSubHeading(rs.getString("sub_heading"));
		subHeading.setMainHeadingId(rs.getInt("main_heading_id"));
		subHeading.setHasMorningSubHeading(rs.getBoolean("morning_sub_heading"));
		subHeading.setHasAfternoonSubHeading(rs.getBoolean("afternoon_sub_heading"));

		return subHeading;
	}

}
