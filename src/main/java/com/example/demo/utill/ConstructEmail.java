/**
 * 
 */
package com.example.demo.utill;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.example.demo.request.EmailsRequest;

/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 7, 2022
 * @version   1.0.0
 */

@Component
public class ConstructEmail {
	
	private final Path fileLocation;

	public ConstructEmail(FileStorageProperties properties) {
		this.fileLocation = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public MimeMessagePreparator constructCustomEmail(EmailsRequest request, String to) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				message.setSubject(request.getEmails().getSubject());
				message.setTo(to);
				message.setFrom(new InternetAddress("shopnobaazmailsender@gmail.com"));
				message.setText(request.getEmails().getBody(), request.getEmails().getIsHtml());

				if (!request.getAttachments().isEmpty()) {
					request.getAttachments().forEach(m -> {
						File file = new File(fileLocation.toString() + "/" + m.getFileUrl());
						if (m.getType().equalsIgnoreCase("inline")) {
							try {
								message.addInline(file.getName(), file);
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							try {
								message.addAttachment(file.getName(), file);
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}

			}
		};
		return preparator;
	}

}
