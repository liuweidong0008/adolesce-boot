package com.boot.redis;

import com.boot.framework.redis.RedisService;
import com.boot.module.users.entity.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class) 
@SpringBootTest
public class RedisTest {
	@Autowired
    private RedisService redisService;
	
	@Test
	public void testSet(){
		redisService.set("test-name","zhangsan");
	}
	
	@Test
	public void testGet(){
		Object oo = redisService.get("test-name");
		System.err.println(oo+"123");
		String str = (String)oo;
		System.err.println(str.length());
	}
	
	@Test
	public void testSetMap() throws ParseException{
		Map<String, Users> userMap = new HashMap<String,Users>();
		Users users = new Users();
		users.setAge(21);
		users.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1991-05-27"));
		users.setId(21l);
		users.setPassWord("123456");
		users.setUserName("lwd");
		userMap.put("01",users);
		redisService.set("test-map",userMap);
	}
	
	@Test
	public void testGetMap(){
		Map<String,Users> userMap  = (Map<String,Users>)redisService.get("test-map");
		System.err.println(userMap);
		System.err.println(userMap.get("01"));
	}

	@Test
	public void testSetUsers() throws ParseException {
		Users users = new Users();
		users.setAge(21);
		users.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1991-05-27"));
		users.setId(21l);
		users.setPassWord("123456");
		users.setUserName("lwd");
		boolean result = redisService.set("test-user",users);
		System.err.println(result);
		Users users2 = (Users)redisService.get("test-user");
		System.err.println(users2);
	}

	@Test
	public void testSetNx(){
		boolean result1 = redisService.setNx1("key.1",1);
		boolean result2 = redisService.setNx2("key.2",1);
		boolean result3 = redisService.setNx3("key.3",1);
		System.err.println(result1);
		System.err.println(result2);
		System.err.println(result3);
	}

	@Test
	public void testLock(){
		boolean result = redisService.lock1("key.1","1",20L);
		System.err.println(result);
	}
}
