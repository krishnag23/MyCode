package com.swire.smo.inspection.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.swire.smo.inspection.dao.QuestionMasterDAO;
import com.swire.smo.inspection.domain.AnswerDetails;
import com.swire.smo.inspection.domain.DistributionEmailDetails;
import com.swire.smo.inspection.domain.DistributionEmailDetailsMapper;
import com.swire.smo.inspection.domain.MainHeading;
import com.swire.smo.inspection.domain.MainHeadingMapper;
import com.swire.smo.inspection.domain.MasterUser;
import com.swire.smo.inspection.domain.MasterUserMapper;
import com.swire.smo.inspection.domain.Question;
import com.swire.smo.inspection.domain.QuestionAnswerJoinForEdit;
import com.swire.smo.inspection.domain.QuestionAnswerJoinForEditMapper;
import com.swire.smo.inspection.domain.QuestionMainHeading;
import com.swire.smo.inspection.domain.QuestionMapper;
import com.swire.smo.inspection.domain.QuestionMaster;
import com.swire.smo.inspection.domain.QuestionResponseDetails;
import com.swire.smo.inspection.domain.QuestionResponseDetailsForEdit;
import com.swire.smo.inspection.domain.QuestionResponseDetailsForEditMapper;
import com.swire.smo.inspection.domain.QuestionSubHeading;
import com.swire.smo.inspection.domain.ReportListDetails;
import com.swire.smo.inspection.domain.ReportListDetailsMapper;
import com.swire.smo.inspection.domain.ReportingMail;
import com.swire.smo.inspection.domain.ReportingMailMapper;
import com.swire.smo.inspection.domain.SubHeading;
import com.swire.smo.inspection.domain.SubHeadingMapper;

/**
 *
 * @author Krishna
 */
@Repository
@Transactional
public class QuestionMasterDAOImpl implements QuestionMasterDAO {

	private static final Logger LOG = LoggerFactory.getLogger(QuestionMasterDAOImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<MasterUser> getMasterUsers() {
		return jdbcTemplate.query("SELECT * FROM tbl_user_master order by user_name;", new MasterUserMapper());
	}

	@Override
	public List<QuestionMainHeading> getQuestionsData() {

		List<QuestionMainHeading> questionDataLst = new ArrayList<>();
		List<MainHeading> mainHeadingLst = getMainHeadingData();
		if (!mainHeadingLst.isEmpty()) {

			for (MainHeading mainHeading : mainHeadingLst) {
				QuestionMainHeading questionMainData = new QuestionMainHeading();
				questionMainData.setId(mainHeading.getId());
				questionMainData.setMainHeading(mainHeading.getMainHeading());
				questionMainData.setHasMorning(mainHeading.isHasMorningMainHeadin());
				questionMainData.setHasAfternoon(mainHeading.isHasAfternoonMainHeadin());
				List<SubHeading> subHeadingLst = getSubHeadingData(mainHeading.getId());
				if (!subHeadingLst.isEmpty()) {
					List<QuestionSubHeading> questionSubHeadingLst = new ArrayList<>();
					for (SubHeading subHeading : subHeadingLst) {
						QuestionSubHeading questionSubHeading = new QuestionSubHeading();
						questionSubHeading.setId(subHeading.getId());
						questionSubHeading.setSubHeading(subHeading.getSubHeading());
						questionSubHeading.setMainHeadingId(subHeading.getMainHeadingId());
						questionSubHeading.setHasMorning(subHeading.isHasMorningSubHeading());
						questionSubHeading.setHasAfternoon(subHeading.isHasAfternoonSubHeading());
						questionSubHeadingLst.add(questionSubHeading);

						List<Question> questionLst = getQuestionData(subHeading.getId());
						if (!questionLst.isEmpty()) {
							List<QuestionMaster> questionMasterLst = new ArrayList<>();
							for (Question question : questionLst) {

								QuestionMaster questionMaster = new QuestionMaster();
								AnswerDetails answerDetails = new AnswerDetails();
								questionMaster.setId(question.getId());
								questionMaster.setQuestion(question.getQuestion());
								questionMaster.setSubHeadingId(question.getSubHeadingId());
								questionMaster.setMainHeadingId(mainHeading.getId());
								questionMaster.setHasMorning(question.isHasMorning());
								questionMaster.setHasAfternoon(question.isHasAfternoon());
								questionMaster.setDescription(question.getDescription());
								questionMaster.setReplyOption(question.getReplyOption());

								questionMaster.setAnswerDetaiils(answerDetails);

								questionMasterLst.add(questionMaster);

							}
							questionSubHeading.setQuestionMaster(questionMasterLst);

						}
						questionMainData.setQuestionSubHeading(questionSubHeadingLst);
					}

				}

				questionDataLst.add(questionMainData);
			}

		}

		return questionDataLst;
	}

	public List<MainHeading> getMainHeadingData() {
		return jdbcTemplate.query("SELECT * FROM tbl_question_main_heading;", new MainHeadingMapper());
	}

	public List<SubHeading> getSubHeadingData(int mainHeadingId) {
		return jdbcTemplate.query("SELECT * FROM tbl_question_sub_heading where main_heading_id =?;", new Object[]{mainHeadingId}, new SubHeadingMapper());
	}

	public List<Question> getQuestionData(int subHeadingId) {
		return jdbcTemplate.query("SELECT * FROM tbl_question_master where sub_heading_id =?;", new Object[]{subHeadingId}, new QuestionMapper());
	}

	@Override
	public int checkRecordExits(QuestionResponseDetails questionResponseDetails) {
		int count = 0;
		String str_date = questionResponseDetails.getReportingDate();
		String dateOfList = questionResponseDetails.getDateOfList();
		Date date1 = new Date();
		Date date2 = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1 = df.parse(str_date);
			date2 = df.parse(dateOfList);
		} catch (ParseException ex) {
			System.out.println("Exception in Date parsing");
		}
		String mydate1 = df.format(date1);
		String mydate2 = df.format(date2);
		if (questionResponseDetails.getReportingDate().equalsIgnoreCase(questionResponseDetails.getDateOfList())) {
			String check = "SELECT count(*) as myCount FROM tbl_report_list WHERE LEVEL = ? AND reporting_date LIKE  '%" + mydate1 + "%' AND has_morning = ? and has_afternoon = ?";
			count = jdbcTemplate.queryForObject(check, new Object[]{questionResponseDetails.getLevel(),
				questionResponseDetails.isHasMorning(), questionResponseDetails.isHasafternoon()}, Integer.class);
		} else {
			String check = "SELECT count(*) as myCount FROM tbl_report_list WHERE LEVEL = ? AND reporting_date LIKE  '%" + mydate2 + "%' AND has_morning = ? and has_afternoon = ?";
			count = jdbcTemplate.queryForObject(check, new Object[]{questionResponseDetails.getLevel(),
				questionResponseDetails.isHasMorning(), questionResponseDetails.isHasafternoon()}, Integer.class);
		}
		return count;

	}

	@Override
	public void saveQuestionResponseDetails(QuestionResponseDetails questionResponseDetails) {

		String sql = "INSERT INTO tbl_question_answer_details (question_Id,reply,unsafe_act_action,action_required,person_in_charge,"
				+ "target_completion_date,status_of_action,date_of_action_completed,user_id,reporting_date,LEVEL,is_morning,"
				+ "is_afternoon,Is_issue_resolved,main_heading_id,sub_heading_id,date_of_list_page) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		//		+ " ON DUPLICATE KEY UPDATE question_Id = ?,reply = ?,unsafe_act_action = ?,action_required = ?,person_in_charge = ?,"
		//		+ "target_completion_date = ?,status_of_action = ?,date_of_action_completed = ?,user_id = ?,reporting_date = ?,"
		//		+ "LEVEL = ?,is_morning = ?,is_afternoon = ?, Is_issue_resolved = ?";
		try {
			int[] pk = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, questionResponseDetails.getQuestionDetails().get(i).getQuestionId());
					ps.setString(2, questionResponseDetails.getQuestionDetails().get(i).getReply());
					ps.setString(3, questionResponseDetails.getQuestionDetails().get(i).getUnsafeAactAction());
					ps.setString(4, questionResponseDetails.getQuestionDetails().get(i).getActionRequired());
					ps.setString(5, questionResponseDetails.getQuestionDetails().get(i).getPersonInCharge());
					ps.setString(6, questionResponseDetails.getQuestionDetails().get(i).getTargetCompletionDate());
					ps.setString(7, questionResponseDetails.getQuestionDetails().get(i).getStatusOfAction());
					ps.setString(8, questionResponseDetails.getQuestionDetails().get(i).getDateOfActionCompleted());
					ps.setString(9, questionResponseDetails.getUserId());
					ps.setString(10, questionResponseDetails.getReportingDate());
					ps.setString(11, questionResponseDetails.getLevel());
					ps.setBoolean(12, questionResponseDetails.isHasMorning());
					ps.setBoolean(13, questionResponseDetails.isHasafternoon());
					ps.setBoolean(14, questionResponseDetails.getQuestionDetails().get(i).isIsIssueResolved());
					ps.setInt(15, questionResponseDetails.getQuestionDetails().get(i).getMainHeadingId());
					ps.setInt(16, questionResponseDetails.getQuestionDetails().get(i).getSubHeadingId());
					ps.setString(17, questionResponseDetails.getDateOfList());

				}

				@Override
				public int getBatchSize() {
					return questionResponseDetails.getQuestionDetails().size();
				}

			});
			List<String> primaryIds = jdbcTemplate.queryForList("SELECT id FROM tbl_question_answer_details where LEVEL =? and user_id = ? and reporting_date = ? and is_morning = ? and is_afternoon = ?;", new Object[]{questionResponseDetails.getLevel(), questionResponseDetails.getUserId(), questionResponseDetails.getReportingDate(),
				questionResponseDetails.isHasMorning(), questionResponseDetails.isHasafternoon()}, String.class);
			List<String> questionIds = jdbcTemplate.queryForList("SELECT question_id FROM tbl_question_answer_details where LEVEL =? and user_id = ? and reporting_date = ? and is_morning = ? and is_afternoon = ?;", new Object[]{questionResponseDetails.getLevel(), questionResponseDetails.getUserId(), questionResponseDetails.getReportingDate(),
				questionResponseDetails.isHasMorning(), questionResponseDetails.isHasafternoon()}, String.class);
			String pid = String.join(",", primaryIds);
			String qIds = String.join(",", questionIds);
			if (questionResponseDetails.getDateOfList() != null) {
				if (questionResponseDetails.getDateOfList().equalsIgnoreCase(questionResponseDetails.getReportingDate())) {

					jdbcTemplate.update("INSERT INTO tbl_report_list (has_morning,has_afternoon,LEVEL,reporting_date,user_id,is_issue_in_report,unresolved_cases,response_ids,question_ids) "
							+ " VALUE  (?,?,?,?,?,?,?,?,?)", questionResponseDetails.isHasMorning(), questionResponseDetails.isHasafternoon(),
							questionResponseDetails.getLevel(), questionResponseDetails.getReportingDate(), questionResponseDetails.getUserId(),
							questionResponseDetails.getIsIssueInReport(), questionResponseDetails.getUnresolvedCases(), pid, qIds);
				} else {
					jdbcTemplate.update("INSERT INTO tbl_report_list (has_morning,has_afternoon,LEVEL,reporting_date,user_id,is_issue_in_report,unresolved_cases,response_ids,question_ids) "
							+ " VALUE  (?,?,?,?,?,?,?,?,?)", questionResponseDetails.isHasMorning(), questionResponseDetails.isHasafternoon(),
							questionResponseDetails.getLevel(), questionResponseDetails.getDateOfList(), questionResponseDetails.getUserId(),
							questionResponseDetails.getIsIssueInReport(), questionResponseDetails.getUnresolvedCases(), pid, qIds);
				}
			} else {
				jdbcTemplate.update("INSERT INTO tbl_report_list (has_morning,has_afternoon,LEVEL,reporting_date,user_id,is_issue_in_report,unresolved_cases,response_ids,question_ids) "
						+ " VALUE  (?,?,?,?,?,?,?,?,?)", questionResponseDetails.isHasMorning(), questionResponseDetails.isHasafternoon(),
						questionResponseDetails.getLevel(), questionResponseDetails.getReportingDate(), questionResponseDetails.getUserId(),
						questionResponseDetails.getIsIssueInReport(), questionResponseDetails.getUnresolvedCases(), pid, qIds);
			}

		} catch (Exception e) {
			LOG.error("********Exception at saveQuestionResponseDetails*********", e);
		}
	}

	@Override
	public void updateQuestionResponseDetails(QuestionResponseDetails questionResponseDetails) {

		String sql = "UPDATE tbl_question_answer_details SET question_Id = ?,reply = ?,unsafe_act_action = ?,action_required = ?,"
				+ "target_completion_date = ?,status_of_action = ?,date_of_action_completed = ?,reporting_date = ?,"
				+ "LEVEL = ?,is_morning = ?,is_afternoon = ?, Is_issue_resolved = ?,main_heading_id=?,sub_heading_id=?,updated_on=?  where id = ?";
		try {
			int[] pk = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, questionResponseDetails.getQuestionDetails().get(i).getQuestionId());
					ps.setString(2, questionResponseDetails.getQuestionDetails().get(i).getReply());
					ps.setString(3, questionResponseDetails.getQuestionDetails().get(i).getUnsafeAactAction());
					ps.setString(4, questionResponseDetails.getQuestionDetails().get(i).getActionRequired());
					ps.setString(5, questionResponseDetails.getQuestionDetails().get(i).getTargetCompletionDate());
					ps.setString(6, questionResponseDetails.getQuestionDetails().get(i).getStatusOfAction());
					ps.setString(7, questionResponseDetails.getQuestionDetails().get(i).getDateOfActionCompleted());
					ps.setString(8, questionResponseDetails.getReportingDate());
					ps.setString(9, questionResponseDetails.getLevel());
					ps.setBoolean(10, questionResponseDetails.isHasMorning());
					ps.setBoolean(11, questionResponseDetails.isHasafternoon());
					ps.setBoolean(12, questionResponseDetails.getQuestionDetails().get(i).isIsIssueResolved());
					ps.setInt(13, questionResponseDetails.getQuestionDetails().get(i).getMainHeadingId());
					ps.setInt(14, questionResponseDetails.getQuestionDetails().get(i).getSubHeadingId());
					ps.setString(15, questionResponseDetails.getUpdatedOn());
					ps.setInt(16, questionResponseDetails.getQuestionDetails().get(i).getId());

				}

				@Override
				public int getBatchSize() {
					return questionResponseDetails.getQuestionDetails().size();
				}

			});

		} catch (Exception e) {
			LOG.error("********Exception at updateQuestionResponseDetails*********", e);
		}
	}

	@Override
	public List<ReportListDetails> getListOfReport(String level) {
		return jdbcTemplate.query("SELECT * FROM tbl_report_list where LEVEL =?;", new Object[]{level}, new ReportListDetailsMapper());
	}

	@Override
	public List<QuestionMainHeading> getQuestionsDataForEdit(int id) {
		ReportListDetails reportListDetails = getListOfReportById(id);
		List<QuestionResponseDetailsForEdit> questionResponseDetailsForEdit = new ArrayList<>();
		List<QuestionMainHeading> questionDataLst = new ArrayList<>();
		java.util.List<QuestionMainHeading> finalList = new ArrayList<>();
		if (reportListDetails != null) {
			questionResponseDetailsForEdit = getQuestionResponseDetails(reportListDetails.getResponseIds());
		}
		if (!questionResponseDetailsForEdit.isEmpty()) {
			for (QuestionResponseDetailsForEdit questionResponseDetailsForEditLst : questionResponseDetailsForEdit) {
				List<MainHeading> mainHeadingLst = getMainHeadingForEdit(questionResponseDetailsForEditLst.getMainHeadingId());
				for (MainHeading mainHeading : mainHeadingLst) {
					QuestionMainHeading questionMainData = new QuestionMainHeading();
					questionMainData.setId(mainHeading.getId());
					questionMainData.setMainHeading(mainHeading.getMainHeading());
					questionMainData.setHasMorning(mainHeading.isHasMorningMainHeadin());
					questionMainData.setHasAfternoon(mainHeading.isHasAfternoonMainHeadin());
					List<SubHeading> subHeadingLst = getSubHeadingForEdit(mainHeading.getId());
					if (!subHeadingLst.isEmpty()) {
						List<QuestionSubHeading> questionSubHeadingLst = new ArrayList<>();
						for (SubHeading subHeading : subHeadingLst) {
							QuestionSubHeading questionSubHeading = new QuestionSubHeading();
							questionSubHeading.setId(subHeading.getId());
							questionSubHeading.setSubHeading(subHeading.getSubHeading());
							questionSubHeading.setMainHeadingId(subHeading.getMainHeadingId());
							questionSubHeading.setHasMorning(subHeading.isHasMorningSubHeading());
							questionSubHeading.setHasAfternoon(subHeading.isHasAfternoonSubHeading());
							questionSubHeadingLst.add(questionSubHeading);
							List<QuestionAnswerJoinForEdit> questAnswerLst = getQuestionAnswerJoinForEdit(reportListDetails.getResponseIds(), mainHeading.getId(), subHeading.getId());
							if (!questAnswerLst.isEmpty()) {
								List<QuestionMaster> questionMasterLst = new ArrayList<>();
								for (QuestionAnswerJoinForEdit questionAns : questAnswerLst) {
									QuestionMaster questionMaster = new QuestionMaster();
									AnswerDetails answerDetails = new AnswerDetails();
									questionMaster.setId(questionAns.getQuestionId());
									questionMaster.setQuestion(questionAns.getQuestion());
									questionMaster.setSubHeadingId(questionAns.getSubHeadingId());
									questionMaster.setMainHeadingId(questionAns.getMainHeadingId());
									questionMaster.setHasMorning(questionAns.isHasMorning());
									questionMaster.setHasAfternoon(questionAns.isHasafternoon());
									questionMaster.setDescription(questionAns.getQuestionDesc());
									questionMaster.setReplyOption(questionAns.getReplyOption());
									answerDetails.setId(questionAns.getResponseid());
									answerDetails.setActionRequired(questionAns.getActionRequired());
									answerDetails.setReply(questionAns.getReply());
									answerDetails.setUnsafeAction(questionAns.getUnsafeAactAction());
									answerDetails.setPersonInCharge(questionAns.getPersonInCharge());
									answerDetails.setStatusOfAction(questionAns.getStatusOfAction());
									answerDetails.setTargetCompletionDate(questionAns.getTargetCompletionDate());
									answerDetails.setDateOfActionCompleted(questionAns.getDateOfActionCompleted());
									questionMaster.setAnswerDetaiils(answerDetails);
									questionMasterLst.add(questionMaster);

								}
								questionSubHeading.setQuestionMaster(questionMasterLst);
							}
							questionMainData.setQuestionSubHeading(questionSubHeadingLst);
						}
					}
					questionDataLst.add(questionMainData);
					finalList = questionDataLst.stream()
							.distinct()
							.collect(Collectors.toList());
				}

			}
		}
		return finalList;
	}

	public ReportListDetails getListOfReportById(int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM tbl_report_list where id =?;", new Object[]{id}, new ReportListDetailsMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<QuestionResponseDetailsForEdit> getQuestionResponseDetails(String id) {
		return jdbcTemplate.query("SELECT * FROM tbl_question_answer_details WHERE id in (" + id + ");", new QuestionResponseDetailsForEditMapper());
	}

	public List<MainHeading> getMainHeadingForEdit(int id) {
		return jdbcTemplate.query("SELECT * FROM tbl_question_main_heading where id =?;", new Object[]{id}, new MainHeadingMapper());
	}

	public List<SubHeading> getSubHeadingForEdit(int id) {
		return jdbcTemplate.query("SELECT  * FROM tbl_question_sub_heading where main_heading_id =?;", new Object[]{id}, new SubHeadingMapper());
	}

	@Override
	public List<DistributionEmailDetails> getDistributionMailIds() {
		List<DistributionEmailDetails> distributionList = new ArrayList<>();

		LOG.debug("********getting Email Ids*******");

		String sqlSelect = "SELECT * FROM tbl_email_distribution_list_master;";
		try {
			distributionList = jdbcTemplate.query(sqlSelect, new DistributionEmailDetailsMapper());
		} catch (Exception e) {
			LOG.error("********Exception at getting Distribution Email", e);
		}

		return distributionList;
	}

	@Override
	public List<ReportingMail> getReportingManagerMailIds() {
		List<ReportingMail> mailIds = new ArrayList<>();
		LOG.debug("********getting ReportingMail Ids*******");

		String sqlSelect = "SELECT * FROM tbl_reporting_manager_email_ids;";
		try {
			mailIds = jdbcTemplate.query(sqlSelect, new ReportingMailMapper());
		} catch (Exception e) {
			LOG.error("********Exception at getting getReportingManagerMailIds", e);
		}

		return mailIds;
	}

	@Override
	public List<Map<String, Object>> getReportForMorning(String level, int hasMorning, String date) {
		String sqlSelect = "SELECT id,has_morning,has_afternoon,`level`,reporting_date,is_issue_in_report,unresolved_cases,DATE_FORMAT(reporting_date,'%a %D %b %Y') AS showdate,DATE_FORMAT(reporting_date,'%H:%i') AS submittedDate,SUBSTRING_INDEX(SUBSTRING_INDEX(user_id,'##',2),'##',-1) AS user_id FROM tbl_report_list where level = ? and has_morning = ? and  reporting_date >= CURDATE();";
		return jdbcTemplate.queryForList(sqlSelect, new Object[]{level, hasMorning});
	}

	@Override
	public List<Map<String, Object>> getReportForAfternoon(String level, int hasAfternoon, String date) {
		String sqlSelect = "SELECT id,has_morning,has_afternoon,`level`,reporting_date,is_issue_in_report,unresolved_cases,DATE_FORMAT(reporting_date,'%a %D %b %Y') AS showdate,DATE_FORMAT(reporting_date,'%H:%i') AS submittedDate,SUBSTRING_INDEX(SUBSTRING_INDEX(user_id,'##',2),'##',-1) AS user_id FROM tbl_report_list where level = ? and has_afternoon = ? and  reporting_date >= CURDATE();";
		return jdbcTemplate.queryForList(sqlSelect, new Object[]{level, hasAfternoon});
	}

	public List<QuestionAnswerJoinForEdit> getQuestionAnswerJoinForEdit(String id, int mainHeading, int subHeading) {
		return jdbcTemplate.query("SELECT qad.id AS response_id,qad.question_Id,qad.reply,qad.unsafe_act_action,qad.action_required,"
				+ " qad.person_in_charge,qad.target_completion_date,qad.status_of_action,qad.date_of_action_completed,"
				+ " qad.user_id,qad.reporting_date,qad.level,qad.Is_issue_resolved,"
				+ " qad.main_heading_id,qad.sub_heading_id,qm.question,qm.is_morning,qm.is_afternoon,qm.description,"
				+ " qm.display_reply FROM tbl_question_answer_details qad JOIN tbl_question_master qm ON qm.id = qad.question_Id "
				//+ " AND qad.main_heading_id = qm.main_heading_id AND qad.sub_heading_id = qm.sub_heading_id "
				+ " AND qad.id IN (" + id + ") and qm.main_heading_id = ? and qm.sub_heading_id =?;", new Object[]{mainHeading, subHeading}, new QuestionAnswerJoinForEditMapper());
	}

//	@Override
//	public List<QuestionMainHeading> getQuestionsDataForEdit(int id) {
//		ReportListDetails reportListDetails = getListOfReportById(id);
//		List<QuestionMainHeading> questionDataLst = new ArrayList<>();
//		List<QuestionResponseDetailsForEdit> questionResponseDetailsForEdit = getQuestionResponseDetails(reportListDetails.getResponseIds(), reportListDetails.getReportingDate(),
//				reportListDetails.isHasMorning(), reportListDetails.isHasAfternoon());
//		QuestionMainHeading questionMainData = new QuestionMainHeading();
//		for (QuestionResponseDetailsForEdit questionResponseDetailsForEditLst : questionResponseDetailsForEdit) {
//			System.out.println("questionResponseDetailsForEditLst" + questionResponseDetailsForEditLst);
//			MainHeading mainHeading = getMainHeadingForEdit(questionResponseDetailsForEditLst.getMainHeadingId());
//			questionMainData.setId(mainHeading.getId());
//			questionMainData.setMainHeading(mainHeading.getMainHeading());
//			questionMainData.setHasMorning(mainHeading.isHasMorningMainHeadin());
//			questionMainData.setHasAfternoon(mainHeading.isHasAfternoonMainHeadin());
//			SubHeading subHeading = getSubHeadingForEdit(questionResponseDetailsForEditLst.getSubHeadingId());
//			List<QuestionSubHeading> questionSubHeadingLst = new ArrayList<>();
//			QuestionSubHeading questionSubHeading = new QuestionSubHeading();
//			questionSubHeading.setId(subHeading.getId());
//			questionSubHeading.setSubHeading(subHeading.getSubHeading());
//			questionSubHeading.setMainHeadingId(subHeading.getMainHeadingId());
//			questionSubHeading.setHasMorning(subHeading.isHasMorningSubHeading());
//			questionSubHeading.setHasAfternoon(subHeading.isHasAfternoonSubHeading());
//			questionSubHeadingLst.add(questionSubHeading);
//			List<Question> questionLst = getQuestionForEdit(reportListDetails.getQuesstionIds());
//			List<QuestionResponseDetailsForEdit> questionResponseDetailsForEdit1 = getQuestionResponseDetails(reportListDetails.getResponseIds(), reportListDetails.getReportingDate(),
//					reportListDetails.isHasMorning(), reportListDetails.isHasAfternoon());
//			if (!questionLst.isEmpty()) {
//				List<QuestionMaster> questionMasterLst = new ArrayList<>();
//				for (Question question : questionLst) {
//					QuestionMaster questionMaster = new QuestionMaster();
//					AnswerDetails answerDetails = new AnswerDetails();
//					questionMaster.setId(question.getId());
//					questionMaster.setQuestion(question.getQuestion());
//					questionMaster.setSubHeadingId(question.getSubHeadingId());
//					questionMaster.setMainHeadingId(mainHeading.getId());
//					questionMaster.setHasMorning(question.isHasMorning());
//					questionMaster.setHasAfternoon(question.isHasAfternoon());
//					questionMaster.setDescription(question.getDescription());
//					questionMaster.setReplyOption(question.getReplyOption());
//					answerDetails.setId(questionResponseDetailsForEditLst.getId());
//					answerDetails.setActionRequired(questionResponseDetailsForEditLst.getActionRequired());
//					answerDetails.setReply(questionResponseDetailsForEditLst.getReply());
//					answerDetails.setUnsafeAction(questionResponseDetailsForEditLst.getUnsafeAactAction());
//					answerDetails.setPersonInCharge(questionResponseDetailsForEditLst.getPersonInCharge());
//					answerDetails.setStatusOfAction(questionResponseDetailsForEditLst.getStatusOfAction());
//					answerDetails.setTargetCompletionDate(questionResponseDetailsForEditLst.getTargetCompletionDate());
//					answerDetails.setDateOfActionCompleted(questionResponseDetailsForEditLst.getDateOfActionCompleted());
//					questionMaster.setAnswerDetaiils(answerDetails);
//					questionMasterLst.add(questionMaster);
//
//				}
//				questionSubHeading.setQuestionMaster(questionMasterLst);
//			}
//			questionMainData.setQuestionSubHeading(questionSubHeadingLst);
//			//questionDataLst.add(questionMainData);
//
//		}
//		questionDataLst.add(questionMainData);
//		return questionDataLst;
//	}
	public File export() {

		String dataSourceFileName = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now());
		String excelFilePath = "/tmp/Reviews-export-" + dataSourceFileName + ".xlsx";

		File file = new File(excelFilePath);

		try {
			String sql = "SELECT qad.id AS response_id,qad.question_Id,qad.reply,qad.unsafe_act_action,qad.action_required,"
					+ " um.user_name as person_in_charge,qad.target_completion_date,qad.status_of_action,qad.date_of_action_completed,"
					+ " qad.user_id,qad.reporting_date,qad.level,qad.Is_issue_resolved,"
					+ " qad.main_heading_id,qad.sub_heading_id,qm.question,qad.is_morning,qad.is_afternoon,qm.description,"
					+ " qm.display_reply FROM tbl_question_answer_details qad JOIN tbl_question_master qm ON qm.id = qad.question_Id "
					+ " LEFT JOIN tbl_user_master um ON um.email_id=qad.person_in_charge;";
			//+ " AND  qad.reporting_date >= CURDATE();";

			List<QuestionAnswerJoinForEdit> lst = jdbcTemplate.query(sql, new QuestionAnswerJoinForEditMapper());
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Report-Reviews");

			writeHeaderLine(sheet);
			try {
				writeDataLines(lst, workbook, sheet);
			} catch (SQLException ex) {
				System.out.println("Exception in writeDataLines:");
			}

			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			System.out.println("File IO error:");
		}
		return file;
	}

	private void writeHeaderLine(XSSFSheet sheet) {

		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("S.no.");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("Date of Report");

		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("Level");

		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("Morning Report");

		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("Afternoon Report");

		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("Reporter Name");

		headerCell = headerRow.createCell(6);
		headerCell.setCellValue("Unsafe act/action");

		headerCell = headerRow.createCell(7);
		headerCell.setCellValue("Action Required");

		headerCell = headerRow.createCell(8);
		headerCell.setCellValue("Person in charge");

		headerCell = headerRow.createCell(9);
		headerCell.setCellValue("Target Completion Date");

		headerCell = headerRow.createCell(10);
		headerCell.setCellValue("Status of Action");

		headerCell = headerRow.createCell(11);
		headerCell.setCellValue("Date of Action Completed");
	}

	private void writeDataLines(List<QuestionAnswerJoinForEdit> result, XSSFWorkbook workbook,
			XSSFSheet sheet) throws SQLException {
		int rowCount = 1;

		for (QuestionAnswerJoinForEdit lst : result) {
			int serialNo = rowCount;
			String reportingDate = lst.getReportingDate();
			String level = lst.getLevel();
			boolean hasMorning = lst.isHasMorning();
			String morningReport = "";
			if (hasMorning) {
				morningReport = "YES";
			} else {
				morningReport = "NO";
			}
			boolean hasAfternoon = lst.isHasafternoon();
			String afternoonReport = "";
			if (hasAfternoon) {
				afternoonReport = "YES";
			} else {
				afternoonReport = "NO";
			}
			String s = lst.getUserId();
			String[] str = s.split("##");
			String userId = str[1];
			String unsafeActAction = lst.getUnsafeAactAction();
			String actionRequired = lst.getActionRequired();
			String personInCharge = lst.getPersonInCharge();
			String targetCompletionDate = lst.getTargetCompletionDate();
			String statusOfAction = lst.getStatusOfAction();
			String dateOfActionCompletion = lst.getDateOfActionCompleted();

			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(serialNo);

			cell = row.createCell(columnCount++);
			cell.setCellValue(reportingDate);

			cell = row.createCell(columnCount++);
			cell.setCellValue(level);

			cell = row.createCell(columnCount++);
			cell.setCellValue(morningReport);

			cell = row.createCell(columnCount++);
			cell.setCellValue(afternoonReport);

			cell = row.createCell(columnCount++);
			cell.setCellValue(userId);

			cell = row.createCell(columnCount++);
			cell.setCellValue(unsafeActAction);

			cell = row.createCell(columnCount++);
			cell.setCellValue(actionRequired);

			cell = row.createCell(columnCount++);
			cell.setCellValue(personInCharge);

			cell = row.createCell(columnCount++);
			cell.setCellValue(targetCompletionDate);

			cell = row.createCell(columnCount++);
			cell.setCellValue(statusOfAction);

			cell = row.createCell(columnCount);
			cell.setCellValue(dateOfActionCompletion);

		}
	}
}
