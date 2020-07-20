
package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class ReportListDetailsMapper implements RowMapper<ReportListDetails> {

	@Override
	public ReportListDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportListDetails reportListDetails = new ReportListDetails();
		reportListDetails.setId(rs.getInt("id"));
		reportListDetails.setHasMorning(rs.getBoolean("has_morning"));
		reportListDetails.setHasAfternoon(rs.getBoolean("has_afternoon"));
		reportListDetails.setLevel(rs.getString("level"));
		reportListDetails.setReportingDate(rs.getString("reporting_date"));
		reportListDetails.setUserId(rs.getString("user_id"));
		reportListDetails.setIssueInReport(rs.getBoolean("is_issue_in_report"));
		reportListDetails.setResponseIds(rs.getString("response_ids"));
		reportListDetails.setQuesstionIds(rs.getString("question_ids"));
		
		
		return reportListDetails;
	}
}
