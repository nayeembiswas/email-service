/**
 * 
 */
package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EmailAttachment;
import com.example.demo.entity.EmailOtherRecipients;

/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 7, 2022
 * @version   1.0.0
 */
public interface EmailAttachmentRepository extends JpaRepository<EmailAttachment, UUID> {
	
	List<EmailAttachment> findByEmailsId(UUID id);

}
