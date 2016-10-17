package com.julyday.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.julyday.entity.User;
import com.julyday.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml","classpath:spring-mvc.xml"})
public class TestService {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test(){
		User u = userService.findByid(1);
		System.out.println(u.toString());
	}
}
