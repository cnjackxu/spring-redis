package com.jacklab.redis.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.jacklab.redis.domain.Employee;

@Repository
public class MyDao {

	private HashMap<String,Employee> map;
	
	public MyDao() {
		map = new HashMap<>();
		map.put("1", new Employee(1,"jack"));
		map.put("2", new Employee(2, "rose"));
	}
	
	public Employee getEmployeeById(String id) {
		return map.get(id);
	}
}
