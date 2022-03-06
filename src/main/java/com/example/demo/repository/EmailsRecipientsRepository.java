package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EmailRecipients;
/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 6, 2022
 * @version   1.0.0
 */
@Repository
public interface EmailsRecipientsRepository extends JpaRepository<EmailRecipients, UUID> {
	
	List<EmailRecipients> findByEmailsId(UUID id);
	
	List<EmailRecipients> findByIsSentAndIsResent(Boolean isSend, Boolean isResent);
	
	List<EmailRecipients> findByIsSentAndIsResentAndEmailsStore(Boolean isSend, Boolean isResent, UUID store);
	
	List<EmailRecipients> findByEmailsStore(UUID store);
}
