package com.swire.smo.inspection.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 *
 * @author Krishna
 */
@Data
public class QuestionMaster implements Serializable {

	private Integer id;

	private String question;

	private Integer subHeadingId;

	private Integer mainHeadingId;

	private String description;

	private boolean hasMorning;

	private boolean hasAfternoon;

	private String[] replyOption;

	private AnswerDetails answerDetaiils;

}
