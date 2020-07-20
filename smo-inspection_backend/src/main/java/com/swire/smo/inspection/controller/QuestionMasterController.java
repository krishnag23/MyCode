package com.swire.smo.inspection.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swire.smo.inspection.domain.MasterUser;
import com.swire.smo.inspection.domain.QuestionDetailsInfo;
import com.swire.smo.inspection.domain.QuestionMainHeading;
import com.swire.smo.inspection.domain.QuestionResponseDetails;
import com.swire.smo.inspection.domain.ReportListDetails;
import com.swire.smo.inspection.dto.SMOResponseObject;
import com.swire.smo.inspection.errors.ErrorCodes;
import com.swire.smo.inspection.service.QuestionMasterService;

/**
 *
 * @author Krishna
 */
@RestController
@RequestMapping("/smo")
public class QuestionMasterController {

	private static final Logger Log = LoggerFactory.getLogger(QuestionMasterController.class);

	@Autowired
	private QuestionMasterService questionMasterService;

	@GetMapping(value = "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SMOResponseObject<QuestionDetailsInfo>> getQuestionData() {
		List<QuestionMainHeading> questionMaster = questionMasterService.getQuestionData();
		List<MasterUser> masterUser = questionMasterService.getMasterUsers();
		QuestionDetailsInfo questionDetailsInfo = new QuestionDetailsInfo();
		questionDetailsInfo.setQuestionMainHeading(questionMaster);
		questionDetailsInfo.setMasterUsers(masterUser);
		SMOResponseObject smoResponseObject = new SMOResponseObject();
		smoResponseObject.setStatusAsSuccessWithMessage(ErrorCodes.SUCCESS_MESSAGE.getMessage());
		smoResponseObject.setData(questionDetailsInfo);
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);
	}

	@PostMapping(value = "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SMOResponseObject<QuestionResponseDetails>> create(@Valid @RequestBody QuestionResponseDetails questionResponseDetails) {
		SMOResponseObject<QuestionResponseDetails> smoResponseObject = new SMOResponseObject<>();
		int count = questionMasterService.checkRecordExits(questionResponseDetails);
		if (count > 0) {
		smoResponseObject.setStatusAsSuccessWithMessage("Record is Already Preseent in Database.");
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);
		} else {
		questionMasterService.saveQuestionResponseDetails(questionResponseDetails);
		smoResponseObject.setData(questionResponseDetails);
		smoResponseObject.setStatusAsSuccessWithMessage(ErrorCodes.SUCCESS_MESSAGE.getMessage());
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);
		}

	}

	@PutMapping(value = "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SMOResponseObject<QuestionResponseDetails>> update(@Valid @RequestBody QuestionResponseDetails questionResponseDetails) {
		SMOResponseObject<QuestionResponseDetails> smoResponseObject = new SMOResponseObject<>();
		questionMasterService.updateQuestionResponseDetails(questionResponseDetails);
		smoResponseObject.setData(questionResponseDetails);
		smoResponseObject.setStatusAsSuccessWithMessage(ErrorCodes.SUCCESS_MESSAGE.getMessage());
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);

	}

	@GetMapping(value = "/v1/getListOfReport/{level}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SMOResponseObject<QuestionDetailsInfo>> getListOfReport(@PathVariable("level") String level) {
		List<ReportListDetails> reportListDetails = questionMasterService.getListOfReport(level);
		SMOResponseObject smoResponseObject = new SMOResponseObject();
		smoResponseObject.setStatusAsSuccessWithMessage(ErrorCodes.SUCCESS_MESSAGE.getMessage());
		smoResponseObject.setData(reportListDetails);
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);
	}

	@GetMapping(value = "/v1/sendMail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SMOResponseObject> sendDailyReportMail() {
		questionMasterService.sendMainForReports();
		SMOResponseObject smoResponseObject = new SMOResponseObject();
		smoResponseObject.setStatusAsSuccessWithMessage(ErrorCodes.SUCCESS_MESSAGE.getMessage());
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/sendMorningReminderMail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SMOResponseObject> sendMorningReminderMail() {
		questionMasterService.sendMorningReminderMail();
		SMOResponseObject smoResponseObject = new SMOResponseObject();
		smoResponseObject.setStatusAsSuccessWithMessage(ErrorCodes.SUCCESS_MESSAGE.getMessage());
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/sendAfternoonReminderMail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SMOResponseObject> sendAfternoonReminderMail() {
		questionMasterService.sendAfternoonReminderMail();
		SMOResponseObject smoResponseObject = new SMOResponseObject();
		smoResponseObject.setStatusAsSuccessWithMessage(ErrorCodes.SUCCESS_MESSAGE.getMessage());
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);
	}
	

	@GetMapping(value = "/v1/getQuestionsDataForEdit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SMOResponseObject<QuestionDetailsInfo>> getQuestionsDataForEdit(@PathVariable("id") int id) {
		List<QuestionMainHeading> getQuestionDetailsForEdit = questionMasterService.getQuestionsDataForEdit(id);
		List<MasterUser> masterUser = questionMasterService.getMasterUsers();
		SMOResponseObject smoResponseObject = new SMOResponseObject();
		QuestionDetailsInfo questionDetailsInfo = new QuestionDetailsInfo();
		questionDetailsInfo.setQuestionMainHeading(getQuestionDetailsForEdit);
		questionDetailsInfo.setMasterUsers(masterUser);
		smoResponseObject.setStatusAsSuccessWithMessage(ErrorCodes.SUCCESS_MESSAGE.getMessage());
		smoResponseObject.setData(questionDetailsInfo);
		return new ResponseEntity<>(smoResponseObject, HttpStatus.OK);
	}

}
