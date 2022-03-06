package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.EmailsRequest;
import com.example.demo.response.CommonResponse;
import com.example.demo.service.EmailsService;
import com.example.demo.utill.CommonUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("email")
public class EmailsController {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(EmailsController.class);
	
	@Autowired
	private EmailsService service;
	
	@Autowired
	private CommonUtils commonUtils;
	
	@PostMapping(value = "/save")
    public CommonResponse save(@RequestBody EmailsRequest alertsRequest){
		try {
    		return commonUtils.generateSuccessResponse(service.save(alertsRequest));
		} catch (Exception e) {
			return commonUtils.generateErrorResponse(e);
		}
    }

}
