package com.boot;

import com.boot.common.redis.RedissonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 集成条件：redis版本2.6.0以上
 * Redisson集成测试
 */
@RunWith(SpringRunner.class) 
@SpringBootTest
public class RedissonTest {
	@Autowired
    private RedissonService redissonService;
	
	@Test
	public void testLock(){
		redissonService.lock("aa");
		try {
			Thread.sleep(70000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			redissonService.unlock("aa");
		}
	}

	@Test
	public void testLockWithTimeout(){
		redissonService.lock("bb",10);
		try {
			Thread.sleep(20000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			redissonService.unlock("bb");
		}
	}

	@Test
	public void testTryLock(){
		redissonService.lock("bb", 10);
		try {
			Thread.sleep(20000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			redissonService.unlock("bb");
		}
	}



}
