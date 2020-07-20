package com.swire.smo.inspection.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swire.smo.inspection.dao.QuestionMasterDAO;
import com.swire.smo.inspection.domain.DistributionEmailDetails;
import com.swire.smo.inspection.domain.MasterUser;
import com.swire.smo.inspection.domain.QuestionMainHeading;
import com.swire.smo.inspection.domain.QuestionResponseDetails;
import com.swire.smo.inspection.domain.ReportListDetails;
import com.swire.smo.inspection.domain.ReportingMail;
import com.swire.smo.inspection.dto.EmailDTO;
import com.swire.smo.inspection.service.QuestionMasterService;
import com.swire.smo.inspection.service.SentAsyncEmailsService;
import com.swire.smo.inspection.util.Constants;

/**
 *
 * @author Krishna
 */
@Service
public class QuestionMasterServiceImpl implements QuestionMasterService {

	private static final Logger LOG = LoggerFactory.getLogger(QuestionMasterServiceImpl.class);

	@Autowired
	private SentAsyncEmailsService sentAsyncEmailsService;

	@Autowired
	private QuestionMasterDAO questionMasterDAO;

	@Override
	public List<MasterUser> getMasterUsers() {
		return questionMasterDAO.getMasterUsers();
	}

	@Override
	public List<QuestionMainHeading> getQuestionData() {
		return questionMasterDAO.getQuestionsData();
	}

	@Override
	public void saveQuestionResponseDetails(QuestionResponseDetails questionResponseDetails) {
		questionMasterDAO.saveQuestionResponseDetails(questionResponseDetails);
	}

	@Override
	public List<ReportListDetails> getListOfReport(String level) {
		return questionMasterDAO.getListOfReport(level);
	}

	@Override
	public void sendMainForReports() {
		sentQuestionDetailsReportEmail();

	}

	@Override
	public List<QuestionMainHeading> getQuestionsDataForEdit(int id) {
		return questionMasterDAO.getQuestionsDataForEdit(id);
	}

	@Override
	public void updateQuestionResponseDetails(QuestionResponseDetails questionResponseDetails) {
		questionMasterDAO.updateQuestionResponseDetails(questionResponseDetails);
	}

	@Override
	public int checkRecordExits(QuestionResponseDetails questionResponseDetails) {
		return questionMasterDAO.checkRecordExits(questionResponseDetails);
	}

	@Override
	public void sendMorningReminderMail() {

		List<DistributionEmailDetails> distributionList = questionMasterDAO.getDistributionMailIds();

		String emailSubject = "Workplace Safety - Morning Reminder Email";
		try {
			if (!distributionList.isEmpty()) {
				EmailDTO emailDTO = new EmailDTO();
				emailDTO.setToAddress(distributionList.get(0).getPrimaryMails().split(";"));

				emailDTO.setSenderId(Constants.MAIL_SENDER);
				emailDTO.setBodyType(Constants.MAIL_BODYTYPE);
				emailDTO.setSubject(emailSubject);
				sentAsyncEmailsService.sentEmailAsyncWithFreeMarkerTemplate("morning-reminder-email.html", distributionList.get(0), emailDTO);

			}
		} catch (Exception e) {
			LOG.error("Exception in sending sendMorningReminderMail for safety Report.", e);
		}
	}

	@Override
	public void sendAfternoonReminderMail() {

		List<DistributionEmailDetails> distributionList = questionMasterDAO.getDistributionMailIds();

		String emailSubject = "Workplace Safety - Afternoon Reminder Email";
		try {
			if (!distributionList.isEmpty()) {
				EmailDTO emailDTO = new EmailDTO();
				emailDTO.setToAddress(distributionList.get(0).getPrimaryMails().split(";"));

				emailDTO.setSenderId(Constants.MAIL_SENDER);
				emailDTO.setBodyType(Constants.MAIL_BODYTYPE);
				emailDTO.setSubject(emailSubject);
				sentAsyncEmailsService.sentEmailAsyncWithFreeMarkerTemplate("afternoon-reminder-email.html", distributionList.get(0), emailDTO);

			}
		} catch (Exception e) {
			LOG.error("Exception in sending sendAfternoonReminderMail for safety Report.", e);
		}
	}

	public void sentQuestionDetailsReportEmail() {

		String level = "27";
		String date = "";
		int hasMorning = 1;
		int hasAfternoon = 1;
		List<Map<String, Object>> emailDataForMorningLevel27 = questionMasterDAO.getReportForMorning(level, hasMorning, date);
		List<Map<String, Object>> emailDataForAfternoonLevel27 = questionMasterDAO.getReportForAfternoon(level, hasAfternoon, date);

		level = "28";
		List<Map<String, Object>> emailDataForMorningLevel28 = questionMasterDAO.getReportForMorning(level, hasMorning, date);
		List<Map<String, Object>> emailDataForAfternoonLevel28 = questionMasterDAO.getReportForAfternoon(level, hasAfternoon, date);

		//Assert.notEmpty(emailDataForLevel27, "Email Data Should not be empty");
		//Assert.notEmpty(emailDataForLevel28, "Email Data Should not be empty");
		List<ReportingMail> distributionList = questionMasterDAO.getReportingManagerMailIds();

		String emailSubject = "Workplace Safety - Daily Satuts Report";
		File file = questionMasterDAO.export();

		List<File> attach = new ArrayList();
		attach.add(file);
		try {
			if (!distributionList.isEmpty()) {
				EmailDTO emailDTO = new EmailDTO();

				emailDTO.setToAddress(distributionList.get(0).getEmailIds().split(";"));

				emailDTO.setSenderId(Constants.MAIL_SENDER);
				emailDTO.setBodyType(Constants.MAIL_BODYTYPE);
				emailDTO.setAttachments(attach);

				emailDTO.setSubject(emailSubject);
				Map map = new HashMap();
				if (!emailDataForMorningLevel27.isEmpty()) {
					map.put("morning27", emailDataForMorningLevel27.get(0));
				}
				if (!emailDataForAfternoonLevel27.isEmpty()) {
					map.put("afternoon27", emailDataForAfternoonLevel27.get(0));
				}
				if (!emailDataForMorningLevel28.isEmpty()) {
					map.put("morning28", emailDataForMorningLevel28.get(0));
				}
				if (!emailDataForAfternoonLevel28.isEmpty()) {
					map.put("afternoon28", emailDataForAfternoonLevel28.get(0));
				}

				sentAsyncEmailsService.sentEmailAsyncWithFreeMarkerTemplate("safetyapp-report.html", map, emailDTO);

			}
		} catch (Exception e) {
			LOG.error("Exception in sending sentQuestionDetailsReportEmail for wider distribution list", e);
		}

	}

}
