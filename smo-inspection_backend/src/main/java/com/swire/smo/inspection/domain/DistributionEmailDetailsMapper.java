
package com.swire.smo.inspection.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class DistributionEmailDetailsMapper implements RowMapper<DistributionEmailDetails> {

	@Override
	public DistributionEmailDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		DistributionEmailDetails distributionDetails = new DistributionEmailDetails();

		distributionDetails.setDistributionId(rs.getInt("distribution_id"));
		distributionDetails.setPrimaryMails(rs.getString("primary_mail"));

		return distributionDetails;
	}

}
