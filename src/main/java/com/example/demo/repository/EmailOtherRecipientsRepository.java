/**
 * 
 */
package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EmailOtherRecipients;
import com.example.demo.entity.EmailRecipients;

/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 6, 2022
 * @version   1.0.0
 */
public interface EmailOtherRecipientsRepository extends JpaRepository<EmailOtherRecipients, UUID> {
	
	List<EmailOtherRecipients> findByEmailsId(UUID id);
	
//	List<EmailOtherRecipients> findByEmailsIdAndIsSentAndIsResent(UUID emailId, Boolean isSend, Boolean isResent);
//		
//	List<EmailOtherRecipients> findByIsSentAndIsResent(Boolean isSend, Boolean isResent);
//	
//	List<EmailOtherRecipients> findByIsSentAndIsResentAndEmailsStore(Boolean isSend, Boolean isResent, UUID store);
	
	List<EmailOtherRecipients> findByEmailsStore(UUID store);

}
