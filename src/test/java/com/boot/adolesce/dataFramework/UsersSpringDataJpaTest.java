package com.boot.adolesce.dataFramework;

import com.boot.adolesce.framework.utils.UUIDUtils;
import com.boot.adolesce.module.users.entity.Address;
import com.boot.adolesce.module.users.entity.Users;
import com.boot.adolesce.module.users.entity.UsersInfo;
import com.boot.adolesce.module.users.repository.AddressRepository;
import com.boot.adolesce.module.users.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  springBoot jpa集成步骤
 *  1、pom依赖mysql-connector-java、com.alibaba druid、spring-boot-starter-data-jpa
 *  2、yml加上spring.datasource.druid配置
 *  3、定义实体Users，表名、字段映射
 *  4、定义UsersRepository，继承JpaRepository<Users, Long>
 *
 *	spring data jpa 操作测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersSpringDataJpaTest {

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private AddressRepository addressRepository;

	/**
	 * 一、basic 操作
	 */

	/**
	 * 单个插入
	 */
	@Test
	public void save() {
		Users users = new Users();
		users.setUserName("a");
		users.setPassWord("11");
		users.setAge(Integer.valueOf(12));
		users.setBirthday(new Date());
		this.usersRepository.save(users);
	}

	/**
	 * 批量插入
	 */
	@Test
	public void saveAll() {
		List<Users> usersList = new ArrayList<Users>();
		Users users;
		for (int i = 1;i<= 10;i++){
			users = new Users();
			users.setId(Long.valueOf(UUIDUtils.getUUID()));
			users.setUserName("u"+(73+i));
			users.setPassWord("p"+(73+i));
			users.setAge(i);
			users.setBirthday(new Date());
			usersList.add(users);
		}
		this.usersRepository.saveAll(usersList);
	}

	/**
	 * 批量插入
	 */
	@Test
	public void saveAllAddress() {
		List<Address> addressList = new ArrayList<Address>();
		Address address;
		for (int i = 1;i<= 10;i++){
			address = new Address("湖南省"+i,"常德市"+i,"鼎城区"+i);
			addressList.add(address);
		}
		this.addressRepository.saveAll(addressList);
	}

	/**
	 * 根据实体删除
	 */
	@Test
	public void delete() {
		Users users = this.usersRepository.findById(38L);
		this.usersRepository.delete(users);
	}

	/**
	 * 根据ID删除
	 */
	@Test
	public void deleteById() {
		this.usersRepository.deleteById(37L);
	}

	/**
	 * 删除所有
	 */
	@Test
	public void deleteAll() {
		this.usersRepository.deleteAll();
	}

	/**
	 * 修改
	 */
	@Test
	public void edit() {
		Users users = this.usersRepository.findById(3L);
		users.setAge(Integer.valueOf(18));
		users.setPassWord("gg123456");
		this.usersRepository.save(users);
	}

	/**
	 * 某个ID是否存在
	 */
	@Test
	public void existsById() {
		Boolean result = this.usersRepository.existsById(40L);
		System.err.println(result);
	}

	/**
	 * 按条件查询是否存在
	 * 对于非字符串属性的只能精确匹配，比如想查询在某个时间段内注册的用户信息，就不能通过Example来查询
	 */
	@Test
	public void exists() {
		Users users = new Users();
		users.setUserName("u2");
		users.setPassWord("p2");
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("userName", match -> match.startsWith()) //%userName
				.withMatcher("passWord", match -> match.contains()); //%passWord%
		//Example<Users> example = Example.of(users);
		Example<Users> example = Example.of(users,matcher);
		Boolean result = this.usersRepository.exists(example);
		System.err.println(result);
	}

	/**
	 * 查询所有记录条数
	 */
	@Test
	public void count() {
		long result = this.usersRepository.count();
		System.err.println(result);
	}

	/**
	 * 按lExample条件查询结果条数
	 */
	@Test
	public void countExample() {
		Users users = new Users();
		users.setUserName("u2");
		Example<Users> example = Example.of(users);
		long result = this.usersRepository.count(example);
		System.err.println(result);
	}

	/**
	 * 查询所有
	 */
	@Test
	public void findAll() {
		List<Users> usersList = this.usersRepository.findAll();
		System.err.println(usersList);
	}

	/**
	 * 查询所有且排序
	 */
	@Test
	public void findAllSort() {
		Sort sort = new Sort(Sort.Direction.DESC, new String[] { "age" });
		List<Users> usersList = this.usersRepository.findAll(sort);
		System.err.println(usersList);
	}

	/**
	 * 按lExample条件查询所有
	 */
	@Test
	public void findAllExample() {
		Users users = new Users();
		users.setUserName("u2");
		Example<Users> example = Example.of(users);
		List<Users> usersList = this.usersRepository.findAll(example);
		System.err.println(usersList);
	}

	/**
	 * 按lExample条件查询所有并排序
	 */
	@Test
	public void findAllExampleSort() {
		Sort sort = new Sort(Sort.Direction.DESC, new String[] { "age" });
		Users users = new Users();
		users.setUserName("u2");
		Example<Users> example = Example.of(users);
		List<Users> usersList = this.usersRepository.findAll(example,sort);
		System.err.println(usersList);
	}

	/**
	 * 根据ID集合查询结果集合
	 */
	@Test
	public void findAllByIds() {
		Long[] ids = {1L,2L,3L};
		List<Users> usersList = this.usersRepository.findAllById(Arrays.asList(ids));
		System.err.println(usersList);
	}

	/**
	 *
	 */
	@Test
	public void findByIdOptional() {
		//Optional<Users> usersOptional = this.usersRepository.findById(40L);
		//System.err.println(usersOptional.get());
	}





	/**
	 * 根据ID查询实体
	 */
	@Test
	public void findById() {
		Users users = this.usersRepository.findById(1L);
		System.err.println(users);
	}

	/**
	 * 根据姓名查询
	 */
	@Test
	public void findByUserName() {
		Users users = this.usersRepository.findByUserName("u6");
		System.err.println(users);
	}

	/**
	 * 根据姓名和年龄查询
	 */
	@Test
	public void findByUserNameAndAge() {
		Users users = this.usersRepository.findByUserNameAndAge("aa", 12);
		System.err.println(users);
	}

	/**
	 * 根据姓名、年龄和密码查询
	 */
	@Test
	public void findByUserNameAndAgeAndPassWord() {
		Users users = this.usersRepository.findByUserNameAndAgeAndPassWord("aa", 12, "a123456");
		System.err.println(users);
	}

	/**
	 * 根据 姓名或（姓名和年龄）查询
	 */
	@Test
	public void findByUserNameOrUserNameAndAge() {
		List<Users> users = this.usersRepository.findByUserNameOrUserNameAndAge("u1", "u2", 12);
		System.err.println(users);
	}

	/**
	 * 根据姓名查询结果条数
	 */
	@Test
	public void countByUserName() {
		long count = this.usersRepository.countByUserName("aa");
		System.err.println(count);
	}

	/**
	 * 根据姓名右模糊查询
	 */
	@Test
	public void findByUserNameLike() {
		List<Users> users = this.usersRepository.findByUserNameLike("u%");
		System.err.println(users);
	}

	/**
	 * 根据姓名忽略大写小查询
	 */
	@Test
	public void findByUserNameIgnoreCase() {
		List<Users> users = this.usersRepository.findByUserNameIgnoreCase("aa");
		System.err.println(users);
	}

	/**
	 * 根据姓名忽略大小写 或者密码模糊查询 根据年龄倒序排
	 */
	@Test
	public void findByUserNameIgnoreCaseOrPassWordLikeOrderByAgeDesc() {
		List<Users> users = this.usersRepository.findByUserNameIgnoreCaseOrPassWordLikeOrderByAgeDesc("aa", "b123456");
		System.err.println(users);
	}

	/**
	 * 根据生日精确查询
	 */
	@Test
	public void findByBirthdayIs() throws ParseException {
		Date birthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2010-01-12 18:02:13");
		List<Users> users = this.usersRepository.findByBirthdayIs(birthday);
		System.err.println(users);
	}

	/**
	 * 根据生日 区间查询
	 */
	@Test
	public void findByBirthdayBetween() throws ParseException {
		Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2010-01-12 18:02:13");
		Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-02-15 14:19:20");
		//startTime<=xx<=endTime
		List<Users> users = this.usersRepository.findByBirthdayBetween(startTime,endTime);
		System.err.println(users);
	}

	/**
	 * 根据生日大于、小于区间 查询
	 */
	@Test
	public void findByBirthdayGreaterThanEqualAndBirthdayLessThanEqual() throws ParseException{
		Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2010-01-12 18:02:13");
		Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-02-15 14:19:20");
		//startTime<=xx<=endTime
		List<Users> users = this.usersRepository.findByBirthdayGreaterThanEqualAndBirthdayLessThanEqual(startTime,endTime);
		System.err.println(users);
	}
	
	/**
	 *	自定义简单查询
	 *	自定义的简单查询就是根据方法名来自动生成SQL，主要的语法是find|read|query|get xxx  by  paramName		countByParamName
	 *  User findByUserName(String userName);
	 *  也使用一些加一些关键字And、 Or
	 *
	 *	User findByUserNameOrEmail(String username, String email);
	 *	修改、删除、统计也是类似语法
	 *
	 *	Long deleteById(Long id);
	 *
	 *	Long countByUserName(String userName)
	 *	基本上SQL体系中的关键词都可以使用，例如：LIKE、 IgnoreCase、 OrderBy。
	 *
	 *	List<User> findByEmailLike(String email);
	 *
	 *	User findByUserNameIgnoreCase(String userName);
	 *
	 *	List<User> findByUserNameOrderByEmailDesc(String email);
	 *	具体的关键字，使用方法和生产成SQL如下表所示
	 *
	 *	Keyword					Sample									JPQL snippet
	 *	And						findByLastnameAndFirstname				… where x.lastname = ?1 and x.firstname = ?2
	 *	Or						findByLastnameOrFirstname				… where x.lastname = ?1 or x.firstname = ?2
	 *	Is,Equals				findByFirstnameIs,findByFirstnameEquals	… where x.firstname = ?1
	 *	Between					findByStartDateBetween					… where x.startDate between ?1 and ?2
	 *	LessThan				findByAgeLessThan						… where x.age < ?1
	 *	LessThanEqual			findByAgeLessThanEqual					… where x.age ⇐ ?1
	 *	GreaterThan				findByAgeGreaterThan					… where x.age > ?1
	 *	GreaterThanEqual		findByAgeGreaterThanEqual				… where x.age >= ?1
	 *	After					findByStartDateAfter					… where x.startDate > ?1
	 *	Before					findByStartDateBefore					… where x.startDate < ?1
	 *	IsNull					findByAgeIsNull							… where x.age is null
	 *	IsNotNull,NotNull		findByAge(Is)NotNull					… where x.age not null
	 *	Like					findByFirstnameLike						… where x.firstname like ?1
	 *	NotLike					findByFirstnameNotLike					… where x.firstname not like ?1
	 *	StartingWith			findByFirstnameStartingWith				… where x.firstname like ?1 (parameter bound with appended %)
	 *	EndingWith				findByFirstnameEndingWith				… where x.firstname like ?1 (parameter bound with prepended %)
	 *	Containing				findByFirstnameContaining				… where x.firstname like ?1 (parameter bound wrapped in %)
	 *	OrderBy					findByAgeOrderByLastnameDesc			… where x.age = ?1 order by x.lastname desc
	 *	Not						findByLastnameNot						… where x.lastname <> ?1
	 *	In						findByAgeIn(Collection ages)			… where x.age in ?1
	 *	NotIn					findByAgeNotIn(Collection age)			… where x.age not in ?1
	 *	TRUE					findByActiveTrue()						… where x.active = true
	 *	FALSE					findByActiveFalse()						… where x.active = false
	 *	IgnoreCase				findByFirstnameIgnoreCase				… where UPPER(x.firstame) = UPPER(?1)
	 *
	 * 	框架已定义的基本方法
	 * 	save(S entity);  --
	 * 	saveAll(Iterable<S> entities);   --
	 * 	saveAndFlush(S entity);
	 *	boolean existsById(ID id); --
	 *  boolean exists(Example<S> example) --
	 *  Optional<T> findById(ID id); --
	 *  Optional<S> findOne(Example<S> example) --
	 *  List<T> findAllById(Iterable<ID> ids); --
	 *	List<T> findAll();   --
	 * 	List<T> findAll(Sort sort); --
	 * 	List<S> findAll(Example<S> example); --
	 * 	List<S> findAll(Example<S> example, Sort sort); --
	 * 	Page<T> findAll(Pageable pageable);  --
	 * 	Page<S> findAll(Example<S> example, Pageable pageable) --
	 * 	long count(); --
	 * 	void deleteById(ID id); --
	 * 	void delete(T entity);
	 * 	void deleteAll(Iterable<? extends T> entities);
	 * 	void deleteAll();
	 * 	void deleteInBatch(Iterable<T> entities);
	 * 	void deleteAllInBatch();
	 * 	T getOne(ID id);
	 * 	long count(Example<S> example) --
	 */
	

	/**
	 * 二、复杂查询
	 */
	
	//分页查询(分页查询在实际使用中非常普遍了，spring data jpa已经帮我们实现了分页的功能，在查询的方法中，需要传入参数Pageable,当查询中有多个参数的时候Pageable建议做为最后一个参数传入,习惯)
	/**
	 *  分页查询所有
	 */
	@Test
	public void findAllWithPage() {
		int pageNum = 1;
		int size = 3;
		Sort sort = new Sort(Sort.Direction.DESC, Arrays.asList(new String[] { "age" }));
		Pageable pageable = new PageRequest(pageNum, size, sort);	//以size为一页，查询第pageNum(0为第一页)页
		//Pageable pageable = new PageRequest(pageNum, size, Sort.Direction.DESC,"age");	//以size为一页，查询第pageNum(0为第一页)页
		Page<Users> page = this.usersRepository.findAll(pageable);
		List<Users> users = page.getContent();
		System.err.println(users);
		System.err.println("Number:" + page.getNumber());	//第几页，同pageNum
		System.err.println("NumberOfElements:" + page.getNumberOfElements());	//查询当前页码数量
		System.err.println("Size:" + page.getSize());	//查询当前页码数量
		System.err.println("TotalElements" + page.getTotalElements());	//元素总数量
		System.err.println("TotalPages:" + page.getTotalPages());	//总页数
		System.err.println("Order:" + page.getSort().getOrderFor("id"));
	}

	/**
	 *  根据姓名进行分页查询
	 */
	@Test
	public void findByUserNamePageable() {
		int pageNum = 0;
		int size = 3;
		Sort sort = new Sort(Sort.Direction.DESC, new String[] { "id" });
		Pageable pageable = new PageRequest(pageNum, size, sort);
		Page<Users> page = this.usersRepository.findByUserName("u10", pageable);
		List<Users> users = page.getContent();
		System.err.println(users);
	}

	//限制查询(有时候我们只需要查询前N个元素，或者支取前一个实体。)
	/*User findTopByOrderByAgeDesc();
	  Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);
	  List<User> findFirst10ByLastname(String lastname, Sort sort);
	  List<User> findTop10ByLastname(String lastname, Pageable pageable);*/
	/**
	 * 根据姓名正向排序取第一个
	 */
	@Test
	public void findFirstByOrderByAgeAsc() {
		Users users = this.usersRepository.findFirstByOrderByAgeAsc();
		System.err.println(users);
	}

	//自定义SQL查询
	/*Spring data 觉大部分的SQL都可以根据方法名定义的方式来实现，但是由于某些原因我们想使用自定义的SQL来查询，spring data也是完美支持的；
	在SQL的查询方法上面使用@Query注解，如涉及到删除和修改在需要加上@Modifying.也可以根据需要添加 @Transactional 对事物的支持，查询超时的设置等*/
	/**
	 * 根据密码进行自定义查询
	 */
	@Test
	public void findByPassWord() {
		List<Users> users = this.usersRepository.myFindByPassWord("p6",new Date());
		System.err.println(users);
	}

	/**
	 * 根据ID自定义更新年龄
	 */
	@Test
	public void updateAgeById() {
		this.usersRepository.myUpdateAgeById(11, 39L);
	}

	/**
	 * 根据usersid对Users、Address进行多表关联查询（返回一行）
	 *
	 */
	@Test
	public void queryUsersInfoByUid(){
		UsersInfo info = this.usersRepository.queryUsersInfoByUid(40L);
		System.err.println(info.getUid());
		System.err.println(info.getUserName());
		System.err.println(info.getAge());
		System.err.println(info.getAid());
		System.err.println(info.getProvince());
		System.err.println(info.getCity());
		System.err.println(info.getArea());
		System.err.println(info.getUsers());
		System.err.println(info.getAddress());
	}

	/**
	 * 根据usersid对Users、Address进行分页多表关联查询（返回多行）
	 */
	@Test
	public void queryUsersInfosByUid(){
		int pageNum = 1;
		int size = 2;
		Sort sort = new Sort(Sort.Direction.DESC, Arrays.asList(new String[] { "a.id" }));
		Pageable pageable = new PageRequest(pageNum, size, sort);	//以size为一页，查询第pageNum(0为第一页)页
		Page<UsersInfo> infos = this.usersRepository.queryUsersInfosByUid(45L,pageable);
		for (UsersInfo info:infos) {
			System.err.println(info.getUid());
			System.err.println(info.getUserName());
			System.err.println(info.getAge());
			System.err.println(info.getAid());
			System.err.println(info.getProvince());
			System.err.println(info.getCity());
			System.err.println(info.getArea());
			System.err.println(info.getUsers());
			System.err.println(info.getAddress());
		}
	}

}