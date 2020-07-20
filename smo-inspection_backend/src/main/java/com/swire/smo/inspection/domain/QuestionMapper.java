package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class QuestionMapper implements RowMapper<Question> {

	@Override
	public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
		Question question = new Question();
		question.setId(rs.getInt("id"));
		question.setQuestion(rs.getString("question"));
		question.setMainHeadingId(rs.getInt("main_heading_id"));
		question.setSubHeadingId(rs.getInt("sub_heading_id"));
		question.setHasMorning(rs.getBoolean("is_morning"));
		question.setHasAfternoon(rs.getBoolean("is_afternoon"));
		question.setDescription(rs.getString("description"));
		String x = rs.getString("display_reply");
		if (x != null && !x.isEmpty()) {
			question.setReplyOption(x.split("#"));
		}
		return question;
	}

}
