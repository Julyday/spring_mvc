package com.julyday.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.julyday.entity.User;
import com.julyday.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Override
	public User findByid(Integer id) {
		System.out.println("UserServiceImpl findByid"+id);
		User u = new User("julyday",18);
		u.setId(1);
		return u;
	}

	@Override
	public List<User> findAll() {
		User u1 = new User("julyday",18);
		u1.setId(1);
		User u2 = new User("zhangsan",28);
		u2.setId(2);
		List<User> list = new ArrayList<User>();
		list.add(u1);
		list.add(u2);
		return list;
	}
	
}
