package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.example.demo.entity.EmailAttachment;
import com.example.demo.entity.EmailOtherRecipients;
import com.example.demo.entity.EmailRecipients;
import com.example.demo.repository.EmailAttachmentRepository;
import com.example.demo.repository.EmailOtherRecipientsRepository;
import com.example.demo.repository.EmailsRecipientsRepository;
import com.example.demo.repository.EmailsRepository;
import com.example.demo.request.EmailsRequest;
import com.example.demo.utill.ConstructEmail;

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
	private ConstructEmail constructEmail;

	@Autowired
	private JavaMailSenderImpl mailSender;




	@Override
	public EmailsRequest save(EmailsRequest request) {
		EmailsRequest saveEntity = new EmailsRequest();
		List<EmailRecipients> recipients = new ArrayList<>();
		List<EmailOtherRecipients> otherRecipients = new ArrayList<>();
		List<EmailAttachment> attachments = new ArrayList<>();

		saveEntity.setEmails(emailsRepository.save(request.getEmails()));

		request.getOtherRecipients().forEach(m -> {
			m.setEmails(saveEntity.getEmails());
			otherRecipients.add(otherRecipientsRepository.save(m));
		});

		request.getRecipients().forEach(m -> {
			m.setEmails(saveEntity.getEmails());
			m.setIsSent(false);
			m.setIsResent(false);
			recipients.add(recipientsRepository.save(m));
		});

		request.getAttachments().forEach(m -> {
			m.setEmails(saveEntity.getEmails());
			attachments.add(attachmentRepository.save(m));
		});

		saveEntity.setRecipients(recipients);
		saveEntity.setOtherRecipients(otherRecipients);
		saveEntity.setAttachments(attachments);

		return saveEntity;
	}

	

	@Transactional
	public void mailSender() {
		List<EmailRecipients> recipients = recipientsRepository.findByIsSentAndIsResent(false, false);

		recipients.forEach(m -> {
			System.out.println(m);
			EmailsRequest request = new EmailsRequest();

			request.setEmails(m.getEmails());
			request.setRecipients(null);
			request.setOtherRecipients(otherRecipientsRepository.findByEmailsId(m.getEmails().getId()));
			request.setAttachments(attachmentRepository.findByEmailsId(m.getEmails().getId()));

			try {
				mailSender.send(constructEmail.constructCustomEmail(request, m.getEmailAddr()));
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




}
