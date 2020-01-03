package com.boot.module.test1;

import com.boot.module.users.entity.Users;

import java.util.List;

public interface User1Mapper {
	
	List<Users> getAll();
	
	Users getOne(Long id);

	void insert(Users user);

	void update(Users user);

	void delete(Long id);

}