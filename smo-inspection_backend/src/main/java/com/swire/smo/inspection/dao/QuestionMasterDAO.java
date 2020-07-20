package com.swire.smo.inspection.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.swire.smo.inspection.domain.DistributionEmailDetails;
import com.swire.smo.inspection.domain.MasterUser;
import com.swire.smo.inspection.domain.QuestionMainHeading;
import com.swire.smo.inspection.domain.QuestionResponseDetails;
import com.swire.smo.inspection.domain.ReportListDetails;
import com.swire.smo.inspection.domain.ReportingMail;

/**
 *
 * @author Krishna
 */
public interface QuestionMasterDAO {

	public List<QuestionMainHeading> getQuestionsData();

	public List<MasterUser> getMasterUsers();

	public void saveQuestionResponseDetails(QuestionResponseDetails questionResponseDetails);

	public List<ReportListDetails> getListOfReport(String level);

	public List<DistributionEmailDetails> getDistributionMailIds();

	public List<Map<String, Object>> getReportForMorning(String level, int hasMorning, String date);

	public List<Map<String, Object>> getReportForAfternoon(String level, int hasAfternoon, String date);

	public List<QuestionMainHeading> getQuestionsDataForEdit(int id);

	public void updateQuestionResponseDetails(QuestionResponseDetails questionResponseDetails);

	public File export();
	
	public List<ReportingMail> getReportingManagerMailIds();

	public int checkRecordExits(QuestionResponseDetails questionResponseDetails);

}
