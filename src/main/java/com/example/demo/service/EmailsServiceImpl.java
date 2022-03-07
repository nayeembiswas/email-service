package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.catalina.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.auth.exception.FileNotFoundException;
import com.example.demo.auth.exception.FileStorageException;
import com.example.demo.constants.MessageConstants;
import com.example.demo.entity.EmailAttachment;
import com.example.demo.entity.EmailOtherRecipients;
import com.example.demo.entity.EmailRecipients;
import com.example.demo.repository.EmailAttachmentRepository;
import com.example.demo.repository.EmailOtherRecipientsRepository;
import com.example.demo.repository.EmailsRecipientsRepository;
import com.example.demo.repository.EmailsRepository;
import com.example.demo.request.EmailsRequest;
import com.example.demo.response.CommonResponse;
import com.example.demo.utill.CommonUtils;
import com.example.demo.utill.FileStorageProperties;

/**
 * @Project email-service
 * @Author Md. Nayeemul Islam
 * @Since Mar 6, 2022
 * @version 1.0.0
 */

@Service
public class EmailsServiceImpl implements EmailsService {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(EmailsServiceImpl.class);

	@Autowired
	private EmailsRecipientsRepository recipientsRepository;

	@Autowired
	private EmailOtherRecipientsRepository otherRecipientsRepository;

	@Autowired
	private EmailAttachmentRepository attachmentRepository;

	@Autowired
	private EmailsRepository emailsRepository;

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private CommonUtils commonUtils;

	private final Path fileLocation;

	public EmailsServiceImpl(FileStorageProperties properties) {
		this.fileLocation = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileLocation);
		} catch (Exception e) {

			throw new FileStorageException("Could not Create the Directory", e);

		}
	}

	private static final int DELAY_SECOND = 10;

	@Override
	public CommonResponse save(EmailsRequest request) {
		EmailsRequest saveEntity = new EmailsRequest();
		List<EmailRecipients> recipients = new ArrayList<>();
		List<EmailOtherRecipients> otherRecipients = new ArrayList<>();
		List<EmailAttachment> attachments = new ArrayList<>();
//		request.getEmails().setIsDeleted(false);

		saveEntity.setEmails(emailsRepository.save(request.getEmails()));

		request.getOtherRecipients().forEach(m -> {
			m.setEmails(saveEntity.getEmails());
//			m.setIsDeleted(false);
			otherRecipients.add(otherRecipientsRepository.save(m));
		});

		request.getRecipients().forEach(m -> {
			m.setEmails(saveEntity.getEmails());
			m.setIsSent(false);
			m.setIsResent(false);
//			m.setIsDeleted(false);
			recipients.add(recipientsRepository.save(m));
		});

		request.getAttachments().forEach(m -> {
			m.setEmails(saveEntity.getEmails());
//			m.setIsDeleted(false);
			attachments.add(attachmentRepository.save(m));
		});

		saveEntity.setRecipients(recipients);
		saveEntity.setOtherRecipients(otherRecipients);
		saveEntity.setAttachments(attachments);
//		saveEntity.setAttachments(attachmentRepository.saveAll(request.getAttachments()));

		return commonUtils.generateSuccessResponse(saveEntity, MessageConstants.SAVE_MESSAGE);
	}

	@Scheduled(fixedDelay = DELAY_SECOND * 1000)
	private void notificationSender() {
		mailSender();
	}

	@Transactional
	private void mailSender() {
		List<EmailRecipients> recipients = recipientsRepository.findByIsSentAndIsResent(false, false);

		recipients.forEach(m -> {
			System.out.println(m);
			EmailsRequest request = new EmailsRequest();

			request.setEmails(m.getEmails());
			request.setRecipients(null);
			request.setOtherRecipients(otherRecipientsRepository.findByEmailsId(m.getEmails().getId()));
			request.setAttachments(attachmentRepository.findByEmailsId(m.getEmails().getId()));

			try {
//				mailSender.send(constructCustomEmail(request, m.getEmailAddr()));
				sendUserRegisterEmail(request, m.getEmailAddr());
				if (m.getIsSent()) {
					m.setIsResent(true);
				} else {
					m.setIsSent(true);
				}
				recipientsRepository.save(m);
			} catch (MailSendException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		});
	}

	public MimeMessagePreparator constructCustomEmail(EmailsRequest request, String to) {

		String[] cc = null;
		String[] bcc = null;

//		request.getOtherRecipients().forEach(m -> {
//			int ccCount = 0;
//			int bccCount = 0;
//			if (m.getIsBcc()) {
//				bcc[bccCount] = m.getEmailAddr();
//				bccCount++;
//			} else {
//				cc[ccCount] = m.getEmailAddr();
//				ccCount++;
//			}
//		});

		return new MimeMessagePreparator() {
			@Override
			public void prepare(javax.mail.internet.MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true);
				email.setTo(to);
//				email.setCc(cc);
//				email.setBcc(bcc);
				email.setSubject(request.getEmails().getSubject());
				email.setText(request.getEmails().getBody());
				email.setFrom(new InternetAddress("shopnobaazmailsender@gmail.com"));
			}
		};
	}

	public void sendUserRegisterEmail(EmailsRequest request, String to) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				message.setSubject(request.getEmails().getSubject());
				message.setTo(to);
				message.setFrom(new InternetAddress("shopnobaazmailsender@gmail.com"));
				message.setText(request.getEmails().getBody(), request.getEmails().getIsHtml());

//	        	MultipartFile image = loadInlineAttachment("image.png", "image/png");
//	            final InputStreamSource imageSource = new ByteArrayResource(image.getBytes());
//	            message.addInline(image.getName(), imageSource,  image.getContentType());

//	            File image2 = new File("D:/webx/project/email-service/file/image.png");
//	        	File pdf2 = new File("D:/webx/project/email-service/file/Fatema-Resume.pdf");
//	            message.addInline(image2.getName(), image2);
//	            message.addAttachment(pdf2.getName(), pdf2);

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
		mailSender.send(preparator);
	}

//	private MultipartFile loadInlineAttachment(String fileName, String contentType) {
//		Path path = Paths.get("D:/webx/project/email-service/file/" + fileName);
//        String originalFileName = fileName;
//        byte[] content = null;
//        try {
//            content = Files.readAllBytes(path);
//        } catch (final IOException e) {
//        }
//        return new MockMultipartFile(fileName,
//                originalFileName, contentType, content);
//	}

//	public Resource loadFileAsResource(String fileName) {
//
//		try {
//			Path filePath = this.productLoc.resolve(fileName).normalize();
//			Resource resource = new UrlResource(filePath.toUri());
//			if (resource.exists()) {
//				return resource;
//			} else {
//				throw new FileNotFoundException("File Not Found");
//			}
//		} catch (MalformedURLException ex) {
//			throw new FileNotFoundException("File not found " + fileName, ex);
//		}
//
//	}

}
