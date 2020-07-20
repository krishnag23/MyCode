package com.swire.smo.inspection.service;

import java.util.List;

import com.swire.smo.inspection.domain.MasterUser;
import com.swire.smo.inspection.domain.QuestionMainHeading;
import com.swire.smo.inspection.domain.QuestionResponseDetails;
import com.swire.smo.inspection.domain.ReportListDetails;
import com.swire.smo.inspection.domain.ReportingMail;

/**
 *
 * @author Krishna
 */
public interface QuestionMasterService {

	public List<QuestionMainHeading> getQuestionData();

	public List<MasterUser> getMasterUsers();

	public void saveQuestionResponseDetails(QuestionResponseDetails questionResponseDetails);

	public List<ReportListDetails> getListOfReport(String level);

	public List<QuestionMainHeading> getQuestionsDataForEdit(int id);

	public void sendMainForReports();

	public void sendMorningReminderMail();

	public void updateQuestionResponseDetails(QuestionResponseDetails questionResponseDetails);

	public int checkRecordExits(QuestionResponseDetails questionResponseDetails);

	public void sendAfternoonReminderMail();

}
