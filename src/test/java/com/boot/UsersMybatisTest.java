package com.boot;

import cn.hutool.core.date.DateUtil;
import com.boot.framework.utils.UUIDUtils;
import com.boot.module.users.entity.Users;
import com.boot.module.users.mapper.annostyle.UsersAnnoMapper;
import com.boot.module.users.mapper.xmlstyle.UsersXmlMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.*;

/**
 *  Mybatis 集成步骤
 *  1、pom依赖mysql-connector-java、com.alibaba druid、mybatis-spring-boot-starter
 *  2、yml加上spring.datasource.druid配置
 *  3、Application打上@MapperScan({ "com.boot.**.mapper*" })注解 指定Mapper接口扫描路径
 *  4、定义实体Users
 *  5、定义Mapper接口、xml文件
 *
 *  另xml方式还需
 *  1、yml指定xml文件路径
 *     mybatis:mapper-locations: classpath*:com/boot/星星/mapper/星星/*.xml
 *  2、pom文件build下加上resource配置指定扫描xml文件
 *      <resource>
 * 				<directory>src/main/java</directory>
 * 				<includes>
 * 					<include>星星/*.xml</include>
 *              </includes>
 *     </resource>
 *
 *	Mybatis xml方式和注解方式 使用测试
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersMybatisTest {

	@Autowired
	private UsersXmlMapper usersMapper;
	@Autowired
	private UsersAnnoMapper usersAnnoMapper;
	//多数据源
	/*@Autowired
	private User1Mapper user1Mapper;
    @Autowired
    private User2Mapper user2Mapper;*/

	@Test
	public void testInsert(){
		this.usersMapper.insert(new Users(1L, "aa", "a123456", 12));
		this.usersMapper.insert(new Users(2L, "bb", "b123456", 13));
		this.usersMapper.insert(new Users(3L, "cc", "b123456", 14));
	}

    @Test
    public void testBatchInsert(){
	    List<Users> usersList = new ArrayList<>();
	    Users users;
	    for (int i = 1;i <= 3;i++){
	        users = new Users();
	        users.setId(Long.valueOf(UUIDUtils.getUUID()));
	        users.setPassWord("password"+i);
	        users.setUserName("username"+i);
	        users.setAge(i);
	        users.setBirthday(DateUtil.date());
            usersList.add(users);
        }
        this.usersMapper.batchInsert(usersList);
    }

    @Test
   public void testDeleteById(){
		this.usersMapper.deleteById(1L);
   }

	@Test
	public void testDeleteByIds(){
		List<Long> ids = new ArrayList<>();
		ids.add(2L);
		ids.add(3L);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("ids",ids);
		this.usersMapper.deleteByIds(paramMap);
	}

	@Test
	public void testDeleteByIdsStr(){
		String idsStr = "41,42";
		this.usersMapper.deleteByIdsStr(idsStr);
	}

	@Test
	public void testUpdate(){
		Users user = this.usersMapper.getById(1L);
		System.out.println(user.toString());
		Date birthday = DateUtil.parse("2010-01-12 18:02:13","yyyy-MM-dd");
		user.setBirthday(birthday);
		this.usersMapper.update(user);
		//Assert.assertTrue("dd".equals(this.usersMapper.getOne(Long.valueOf(1L)).getUserName()));
	}

	@Test
	public void testQueryOne(){
		Users users = this.usersMapper.getById(1L);
		System.err.println(users);
	}

	@Test
	public void testQueryByParam() throws ParseException {
		Users user = new Users();
		//user.setUserName("u1");
		user.setBirthday(DateUtil.parse("2019-09-23 07:56:54","yyyy-MM-dd HH:mm:ss"));
		//user.setStartTime("2019-09-20");
		//user.setEndTime("2019-09-23");
		List<Users> list = this.usersMapper.queryByParam(user);
		list.stream().forEach(System.out::println);
		System.err.println(list);
	}


    /**
     * 多数据源
     */
	/*@Test
	public void testMybatisDataSource1QueryOne(){
		Users users = this.user1Mapper.getOne(1L);
		System.err.println(users);
	}
    @Test
    public void testMybatisDataSource2QueryOne(){
        Users users = this.user2Mapper.getOne(1L);
        System.err.println(users);
    }*/
}