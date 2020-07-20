package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class QuestionResponseDetailsForEditMapper implements RowMapper<QuestionResponseDetailsForEdit> {

	@Override
	public QuestionResponseDetailsForEdit mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuestionResponseDetailsForEdit questionResponseDetailsForEdit = new QuestionResponseDetailsForEdit();
		questionResponseDetailsForEdit.setId(rs.getInt("id"));
		questionResponseDetailsForEdit.setQuestionId(rs.getInt("question_Id"));
		questionResponseDetailsForEdit.setReply(rs.getString("reply"));
		questionResponseDetailsForEdit.setUnsafeAactAction(rs.getString("unsafe_act_action"));
		questionResponseDetailsForEdit.setActionRequired(rs.getString("action_required"));
		questionResponseDetailsForEdit.setPersonInCharge(rs.getString("person_in_charge"));
		questionResponseDetailsForEdit.setTargetCompletionDate(rs.getString("target_completion_date"));
		questionResponseDetailsForEdit.setStatusOfAction(rs.getString("status_of_action"));
		questionResponseDetailsForEdit.setDateOfActionCompleted(rs.getString("date_of_action_completed"));
		questionResponseDetailsForEdit.setUserId(rs.getString("user_id"));
		questionResponseDetailsForEdit.setReportingDate(rs.getString("reporting_date"));
		questionResponseDetailsForEdit.setLevel(rs.getString("level"));
		questionResponseDetailsForEdit.setHasMorning(rs.getBoolean("is_morning"));
		questionResponseDetailsForEdit.setHasafternoon(rs.getBoolean("is_afternoon"));
		questionResponseDetailsForEdit.setIsIssueResolved(rs.getBoolean("Is_issue_resolved"));
		questionResponseDetailsForEdit.setMainHeadingId(rs.getInt("main_heading_id"));
		questionResponseDetailsForEdit.setSubHeadingId(rs.getInt("sub_heading_id"));
		
		return questionResponseDetailsForEdit;
	}

}
