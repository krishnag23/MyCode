package com.swire.smo.inspection.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.swire.smo.inspection.dto.EmailDTO;
import com.swire.smo.inspection.util.EmailUtility;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *
 * @author Sony George : sony.george@swirecnco.com
 */
@Service
public class SentAsyncEmailsService {

	private static final Logger LOG = LoggerFactory.getLogger(SentAsyncEmailsService.class);
	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private Configuration freemarkerConfiguration;

	@Autowired
	private EmailUtility emailUtility;

	/**
	 * @param emailDTO
	 */
	public void sentEmailAsync(EmailDTO emailDTO) {

		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {

				emailUtility.sendMail(emailDTO);

			}
		});
	}

	/**
	 *
	 * @param templateName only name of the template, path not required we
	 * assume that all templates are in folder classpath:/templates/emails/
	 * @param model should have all the values required in the template
	 * @param emailDTO should be set all the required values before passing to
	 * this method, only body is set in this method built from template
	 */
	public void sentEmailAsyncWithFreeMarkerTemplate(String templateName, Object model, EmailDTO emailDTO) {

		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {

				try {

					Template freemarkerTemplate = freemarkerConfiguration.getTemplate(templateName, "utf-8");

					String body = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);

					emailDTO.setBody(body);

					emailUtility.sendMail(emailDTO);

				} catch (Exception e) {
					LOG.error("Exception inside sentEmailAsyncWithFreeMarkerTemplate", e);
					throw new RuntimeException(e);
				}
			}
		});
	}
}
