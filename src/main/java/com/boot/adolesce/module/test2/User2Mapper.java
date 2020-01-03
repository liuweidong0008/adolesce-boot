package com.boot.adolesce.module.test2;

import com.boot.adolesce.module.users.entity.Users;

import java.util.List;

public interface User2Mapper {
	
	List<Users> getAll();

	Users getOne(Long id);

	void insert(Users user);

	void update(Users user);

	void delete(Long id);

}