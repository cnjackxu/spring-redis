package com.jacklab.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacklab.redis.dao.MyDao;
import com.jacklab.redis.domain.Employee;
import com.jacklab.redis.util.RedisUtils;

@Service
public class MyService {

	private static final String REDIS_EMP_KEY="emp";
	
	@Autowired
	private MyDao myDao;
	
	@Autowired
	private RedisUtils redisUtils;
	
	public Employee getEmployeeById(String id) throws JsonProcessingException {
		
		if(redisUtils.hHasKey(REDIS_EMP_KEY, id)) {
			System.out.println("Cache found...");
			String ret = redisUtils.hGet(REDIS_EMP_KEY, id).toString();
			Employee emp = new ObjectMapper().readValue(ret, Employee.class);
			return emp;
		}else {
			 Employee employee = myDao.getEmployeeById(id);
			 String jsonEmp = new ObjectMapper().writeValueAsString(employee);
			 System.out.println("No Cache Found. Read from DB");
			redisUtils.hSet(REDIS_EMP_KEY, id, jsonEmp);
			return employee;
		}
		
	}
}
