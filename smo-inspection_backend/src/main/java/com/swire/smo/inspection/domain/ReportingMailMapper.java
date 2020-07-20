
package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class ReportingMailMapper implements RowMapper<ReportingMail> {

	@Override
	public ReportingMail mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportingMail reportingMail = new ReportingMail();
		reportingMail.setId(rs.getInt("id"));
		reportingMail.setEmailIds(rs.getString("email_ids"));
		return reportingMail;
		
	}
	
}
