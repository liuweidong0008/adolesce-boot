package com.boot.common.mail;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailSendService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	/**
	 * 
	 * @param to
	 *            发送目的邮箱
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 */
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(this.from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		try {
			this.mailSender.send(message);
			this.logger.info("简单邮件已经发送。");
		} catch (Exception e) {
			this.logger.error("发送简单邮件时发生异常！", e);
		}
	}

	/**
	 * 
	 * @param to
	 *            发送目的邮箱
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachmentPaths
	 *            附件地址
	 * @param inlinePaths
	 *            静态资源地址(一般是邮件图片)
	 */
	public void sendMail(String to, String subject, String content, List<String> attachmentPaths,
			Map<String, String> inlinePaths) {
		MimeMessage message = this.mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(this.from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);

			FileSystemResource res = null;
			File file = null;
			if ((attachmentPaths != null) && (attachmentPaths.size() > 0)) {
				for (String filePath : attachmentPaths) {
					file = new File(filePath);
					res = new FileSystemResource(new File(filePath));
					helper.addAttachment(file.getName(), res);
				}
			}

			if ((inlinePaths != null) && (inlinePaths.size() > 0)) {
				for (Map.Entry<String, String> entry : inlinePaths.entrySet()) {
					res = new FileSystemResource(new File((String) entry.getValue()));
					helper.addInline((String) entry.getKey(), res);
				}
			}
			this.mailSender.send(message);
			this.logger.info("邮件已经发送。");
		} catch (MessagingException e) {
			this.logger.error("邮件发生异常！", e);
		}
	}
}