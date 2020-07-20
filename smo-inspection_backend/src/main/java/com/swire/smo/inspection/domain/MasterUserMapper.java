package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Krishna
 */
public class MasterUserMapper implements RowMapper<MasterUser> {

	@Override
	public MasterUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		MasterUser masterUser = new MasterUser();
		masterUser.setId(rs.getInt("id"));
		masterUser.setUserName(rs.getString("user_name"));
		masterUser.setEmaiId(rs.getString("email_id"));

		return masterUser;

	}

}
