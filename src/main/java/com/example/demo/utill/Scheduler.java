/**
 * 
 */
package com.example.demo.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.EmailsServiceImpl;

/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 7, 2022
 * @version   1.0.0
 */
@Component
public class Scheduler {
	
	private static final int DELAY_SECOND = 10;
	
	@Autowired
	private EmailsServiceImpl emailsService;
	
	
	@Scheduled(fixedDelay = DELAY_SECOND * 1000)
	private void notificationSender() {
		emailsService.mailSender();
	}

}
