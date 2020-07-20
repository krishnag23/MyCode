package com.swire.smo.inspection.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swire.aws.SecretManager;
import com.swire.smo.inspection.dto.EmailDTO;
import com.swire.smo.inspection.util.Constants;

/**
 * The Class EmailUtility is a Utility Class set Mail Configurations and to send
 * a mail.
 */
@PropertySource("classpath:email.properties")
@Component
public class EmailUtility implements EnvironmentAware {

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtility.class);

	/**
	 * The environment.
	 */
	@Autowired
	private Environment environment;

	/**
	 * Sets the environment.
	 *
	 * @param environment the new environment
	 */
	@Override
	public void setEnvironment(final Environment environment) {
		this.environment = environment;
	}

	/**
	 * Send mail.
	 *
	 * @param emailDTO the email DTO
	 */
	public void sendMail(EmailDTO emailDTO) {

		Message message = null;
		try {

			message = prepareMessage(emailDTO);

			Transport.send(message);

		} catch (MessagingException | IOException e) {
			LOGGER.error("Exception at Sending Mail", e);
		}

	}

	/**
	 * Prepare message.
	 *
	 * @param emailDTO the email DTO
	 * @return the message
	 * @throws MessagingException the messaging exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Message prepareMessage(EmailDTO emailDTO) throws MessagingException, IOException {

		String profile = environment.getProperty("spring.profiles.active");

		LOGGER.info("profile ={}", profile);
		Message message = null;

		if (profile == null || "default".equals(profile)) {

			Properties prop = getProperties(profile);
			String senderMailId = "swirevessel.monitor@swirecnco.com";

			message = getMessage(emailDTO, senderMailId, "Vaw16170", prop);
		}

		if ("dev".equals(profile)) {
			String secretName = "swire.dev.linerops.email.credentials";
			LOGGER.info("AWS SecretName ={}", secretName);
			ObjectMapper mapper = new ObjectMapper();

			String secret = SecretManager.getSecret(secretName);
			Map<String, String> map = mapper.readValue(secret, Map.class);
			LOGGER.info("AWS secret = {}", secret);
			String senderMailId = map.get("SENDER_MAIL_ID");
			LOGGER.info("sender MailId={}", senderMailId);
			String senderPassword = map.get("SENDER_MAIL_PASSWORD");
			Properties prop = getProperties(profile);
			message = getMessage(emailDTO, senderMailId, senderPassword, prop);
		}

		if ("stage".equals(profile)) {

			String secretName = "swire.stage.linerops.email.credentials";
			LOGGER.info("AWS secretName ={}", secretName);
			ObjectMapper mapper = new ObjectMapper();

			String secret = SecretManager.getSecret(secretName);
			Map<String, String> map = mapper.readValue(secret, Map.class);
			LOGGER.info("AWS secret ={}", secret);
			String senderMailId = map.get("SENDER_MAIL_ID");
			LOGGER.info("sender MailId={}", senderMailId);
			String senderPassword = map.get("SENDER_MAIL_PASSWORD");
			Properties prop = getProperties(profile);
			message = getMessage(emailDTO, senderMailId, senderPassword, prop);
		}

		if ("prod".equals(profile)) {

			String secretName = "swire.prod.linerops.email.credentials";
			LOGGER.info("AWS secretName ={}", secretName);
			ObjectMapper mapper = new ObjectMapper();

			String secret = SecretManager.getSecret(secretName);
			Map<String, String> map = mapper.readValue(secret, Map.class);
			LOGGER.info("AWS secret ={}", secret);

			String senderMailId = map.get("SENDER_MAIL_ID");
			LOGGER.info("senderMailId={}", senderMailId);
			String senderPassword = map.get("SENDER_MAIL_PASSWORD");
			Properties prop = getProperties(profile);
			message = getMessage(emailDTO, senderMailId, senderPassword, prop);

		}

		return message;
	}

	/**
	 * Gets the message.
	 *
	 * @param emailDTO the email DTO
	 * @param senderMailId the sender mail id
	 * @param senderPassword the sender password
	 * @param props the props
	 * @return the message
	 * @throws MessagingException the messaging exception
	 */
	private Message getMessage(EmailDTO emailDTO, String senderMailId, String senderPassword, Properties props)
			throws MessagingException {

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderMailId, senderPassword);
			}
		}); // Prepare email message

		LOGGER.info("senderMailId={}", senderMailId);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(senderMailId));

		if (emailDTO.getToAddress() != null && emailDTO.getToAddress().length > 0) {
			InternetAddress[] toAddress = new InternetAddress[emailDTO.getToAddress().length];

			// To get the array of toaddresses
			for (int i = 0; i < emailDTO.getToAddress().length; i++) {
				toAddress[i] = new InternetAddress(emailDTO.getToAddress()[i]);
			}
			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO, toAddress);
		}
		if (emailDTO.getCcAddress() != null && emailDTO.getCcAddress().length > 0) {

			InternetAddress[] ccAddress = new InternetAddress[emailDTO.getCcAddress().length];

			// To get the array of ccaddresses
			for (int i = 0; i < emailDTO.getCcAddress().length; i++) {
				ccAddress[i] = new InternetAddress(emailDTO.getCcAddress()[i]);
			}

			// Set cc: header field of the header.
			message.addRecipients(Message.RecipientType.CC, ccAddress);
		}
		if (emailDTO.getBccAddress() != null && emailDTO.getBccAddress().length > 0) {

			InternetAddress[] bccAddress = new InternetAddress[emailDTO.getBccAddress().length];

			// To get the array of bccaddresses
			for (int i = 0; i < emailDTO.getBccAddress().length; i++) {
				bccAddress[i] = new InternetAddress(emailDTO.getBccAddress()[i]);
			}

			// Set bcc: header field of the header.
			message.addRecipients(Message.RecipientType.BCC, bccAddress);
		}

		message.setSubject(getsubject(emailDTO.getSubject()));
		if (emailDTO.getAttachments() == null || emailDTO.getAttachments().isEmpty()) {
			message.setContent(emailDTO.getBody(), emailDTO.getBodyType());
		} else {
			message.setContent(getMultipart(emailDTO));
		}
		return message;
	}

	private Multipart getMultipart(EmailDTO emailDTO) {
		Multipart multipart = new MimeMultipart();
		try {
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(emailDTO.getBody(), emailDTO.getBodyType());

			// creates multi-part
			multipart.addBodyPart(messageBodyPart);

			// adds attachments
			for (File file : emailDTO.getAttachments()) {

				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(file);
				} catch (IOException ex) {
					LOGGER.error("Exception while attaching file to email", ex);
				}

				multipart.addBodyPart(attachPart);
			}
		} catch (Exception e) {
			LOGGER.error("Exception while getMultipart to email for attachement", e);
		}
		return multipart;

	}

	/**
	 * Gets the properties.
	 *
	 * @param profile the profile
	 * @return the properties
	 */
	private Properties getProperties(String profile) {

		Properties props = new Properties();

		if (profile == null || "default".equals(profile)) {
			props.put(Constants.MAIL_SMTP_AUTH, "true");
			props.put(Constants.MAIL_SMTP_STARTTLS_ENABLE, "true");
			props.put(Constants.MAIL_SMTP_HOST, "smtp.office365.com");
			props.put(Constants.MAIL_SMTP_PORT, "587");
		}
		if ("dev".equals(profile)) {
			props.put(Constants.MAIL_SMTP_AUTH, "true");
			props.put(Constants.MAIL_SMTP_STARTTLS_ENABLE, "true");
			props.put(Constants.MAIL_SMTP_HOST, "smtp.office365.com");
			props.put(Constants.MAIL_SMTP_PORT, "587");
		}
		if ("stage".equals(profile)) {
			props.put(Constants.MAIL_SMTP_AUTH, "true");
			props.put(Constants.MAIL_SMTP_STARTTLS_ENABLE, "true");
			props.put(Constants.MAIL_SMTP_HOST, "smtp.office365.com");
			props.put(Constants.MAIL_SMTP_PORT, "587");
		}
		if ("prod".equals(profile)) {
			props.put(Constants.MAIL_SMTP_AUTH, "true");
			props.put(Constants.MAIL_SMTP_STARTTLS_ENABLE, "true");
			props.put(Constants.MAIL_SMTP_HOST, "smtp.office365.com");
			props.put(Constants.MAIL_SMTP_PORT, "587");
		}

		return props;
	}

	private String getsubject(String subject) {
		String profile = environment.getProperty("spring.profiles.active");
		if (profile == null || "default".equals(profile)) {
			String user = System.getProperty("user.name");
			return "local ::: " + user + "|" + subject;
		} else {
			return "prod".equals(profile) ? subject : profile + " ::: " + subject;
		}
	}

}
