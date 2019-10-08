package com.boot.users.entity;

public interface UsersInfo {
	Long getUid();
	String getUserName();
	Integer getAge();

	Long getAid();
	String getProvince();
	String getCity();
	String getArea();

	Users getUsers();
	Address getAddress();
}