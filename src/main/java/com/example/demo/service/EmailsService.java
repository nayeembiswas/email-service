package com.example.demo.service;

import com.example.demo.request.EmailsRequest;
import com.example.demo.response.CommonResponse;

/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 6, 2022
 * @version   1.0.0
 */

public interface EmailsService {
	
	public CommonResponse save(EmailsRequest alertsRequest);
}
