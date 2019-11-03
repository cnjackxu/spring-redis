package com.jacklab.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jacklab.redis.domain.Employee;
import com.jacklab.redis.service.MyService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@Autowired
	MyService myService;
	
	@GetMapping("/emp/{id}")
	public Employee getEmployee(@PathVariable String id) {
		try {
			return myService.getEmployeeById(id);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
