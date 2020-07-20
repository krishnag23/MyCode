
package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class QuestionAnswerJoinForEditMapper implements RowMapper<QuestionAnswerJoinForEdit> {
	

	@Override
	public QuestionAnswerJoinForEdit mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuestionAnswerJoinForEdit questionAnswerJoinForEdit = new QuestionAnswerJoinForEdit();
        questionAnswerJoinForEdit.setResponseid(rs.getInt("response_id"));
		questionAnswerJoinForEdit.setQuestionId(rs.getInt("question_Id"));
		questionAnswerJoinForEdit.setReply(rs.getString("reply"));
		questionAnswerJoinForEdit.setUnsafeAactAction(rs.getString("unsafe_act_action"));
		questionAnswerJoinForEdit.setActionRequired(rs.getString("action_required"));
		questionAnswerJoinForEdit.setPersonInCharge(rs.getString("person_in_charge"));
		questionAnswerJoinForEdit.setTargetCompletionDate(rs.getString("target_completion_date"));
		questionAnswerJoinForEdit.setStatusOfAction(rs.getString("status_of_action"));
		questionAnswerJoinForEdit.setDateOfActionCompleted(rs.getString("date_of_action_completed"));
		questionAnswerJoinForEdit.setUserId(rs.getString("user_id"));
		questionAnswerJoinForEdit.setReportingDate(rs.getString("reporting_date"));
		questionAnswerJoinForEdit.setLevel(rs.getString("level"));
		questionAnswerJoinForEdit.setHasMorning(rs.getBoolean("is_morning"));
		questionAnswerJoinForEdit.setHasafternoon(rs.getBoolean("is_afternoon"));
		questionAnswerJoinForEdit.setIsIssueResolved(rs.getBoolean("Is_issue_resolved"));
		questionAnswerJoinForEdit.setMainHeadingId(rs.getInt("main_heading_id"));
		questionAnswerJoinForEdit.setSubHeadingId(rs.getInt("sub_heading_id"));
		questionAnswerJoinForEdit.setQuestion(rs.getString("question"));
		questionAnswerJoinForEdit.setQuestionDesc(rs.getString("description"));
		String x = rs.getString("display_reply");
		if (x != null && !x.isEmpty()) {
			questionAnswerJoinForEdit.setReplyOption(x.split("#"));
		}
		return questionAnswerJoinForEdit;
	}

}
