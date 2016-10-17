package com.julyday.service;

import java.util.List;
import com.julyday.entity.User;

public interface UserService {
	
	User findByid(Integer id);
	
	List<User> findAll();
}
