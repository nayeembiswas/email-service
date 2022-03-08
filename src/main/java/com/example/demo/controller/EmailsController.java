package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.EmailsRequest;
import com.example.demo.response.AppResponse;
import com.example.demo.service.EmailsService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("email")
public class EmailsController {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(EmailsController.class);
	
	@Autowired
	private EmailsService service;
	
	
	@PostMapping(value = "/save")
    public AppResponse save(@RequestBody EmailsRequest request){
		try {
    		return AppResponse.build(HttpStatus.OK).body(service.save(request));
		} catch (Exception e) {
			return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
		}
    }

}
