ALTER TABLE `tbl_question_answer_details`
	CHANGE COLUMN `target_completion_date` `target_completion_date` VARCHAR(50) NULL DEFAULT NULL AFTER `person_in_charge`,
	CHANGE COLUMN `date_of_action_completed` `date_of_action_completed` VARCHAR(50) NULL DEFAULT NULL AFTER `status_of_action`,
	CHANGE COLUMN `reporting_date` `reporting_date` VARCHAR(50) NULL DEFAULT NULL AFTER `user_id`;

ALTER TABLE `tbl_question_answer_details`
	ADD COLUMN `main_heading_id` INT NULL DEFAULT '0' AFTER `Is_issue_resolved`,
	ADD COLUMN `sub_heading_id` INT NULL DEFAULT '0' AFTER `main_heading_id`;
