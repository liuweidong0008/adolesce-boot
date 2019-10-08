package com.boot.users.mapper.annostyle;

import com.boot.users.entity.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UsersAnnoMapper {
	@Select({ "SELECT * FROM users order by birthday" })
	@Results({
			// @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "passWord", column = "pass_word") })
	List<Users> getAll();

	@Select({ "SELECT * FROM users WHERE id = #{id}" })
	@Results({ @Result(property = "userName", column = "user_name"),
			@Result(property = "passWord", column = "pass_word") })
	Users getById(Long paramLong);

	@Insert({ "INSERT INTO users(id,user_name,pass_word,age,birthday) VALUES(#{id}, #{userName}, #{passWord}, #{age}, #{birthday})" })
	void insert(Users paramUsers);

	@Update({ "UPDATE users SET user_name=#{userName},pass_word=#{passWord},age=#{age},birthday=#{birthday} WHERE id =#{id}" })
	void update(Users paramUsers);

	@Delete({ "DELETE FROM users WHERE id =#{id}" })
	void deleteById(Long paramLong);
}