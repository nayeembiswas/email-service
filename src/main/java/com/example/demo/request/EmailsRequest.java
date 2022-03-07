package com.example.demo.request;

import java.util.List;

import com.example.demo.entity.EmailAttachment;
import com.example.demo.entity.EmailOtherRecipients;
import com.example.demo.entity.EmailRecipients;
import com.example.demo.entity.Emails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 6, 2022
 * @version   1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailsRequest {
	
	private Emails emails;
	
	private List<EmailRecipients> recipients;
	
	private List<EmailOtherRecipients> otherRecipients;
	
	private List<EmailAttachment> attachments;

}
