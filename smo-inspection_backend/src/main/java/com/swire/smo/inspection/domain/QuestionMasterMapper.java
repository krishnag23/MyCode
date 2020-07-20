package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class QuestionMasterMapper implements RowMapper<QuestionMaster> {

	@Override
	public QuestionMaster mapRow(ResultSet rs, int rowNum) throws SQLException {

		QuestionMaster questionMaster = new QuestionMaster();
		questionMaster.setId(rs.getInt("id"));
		return questionMaster;
	}

}
