package com.boot.users.repository;

import com.boot.users.entity.Users;
import com.boot.users.entity.UsersInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

//import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findById(long paramLong);

	Users findByUserName(String paramString);

	Users findByUserNameAndAge(String paramString, int paramInt);

	Users findByUserNameAndAgeAndPassWord(String paramString1, int paramInt, String paramString2);

	List<Users> findByUserNameOrUserNameAndAge(String paramString1, String paramString2, int paramInt);

	long countByUserName(String paramString);

	List<Users> findByUserNameLike(String paramString);

	List<Users> findByUserNameIgnoreCase(String paramString);

	List<Users> findByUserNameIgnoreCaseOrPassWordLikeOrderByAgeDesc(String userName,
			String passWord);
	
	List<Users> findByBirthdayIs(Date birthday);
	
	List<Users> findByBirthdayBetween(Date startTime, Date endTime);
	
	List<Users> findByBirthdayGreaterThanEqualAndBirthdayLessThanEqual(Date startTime, Date endTime);

	Page<Users> findByUserName(String paramString, Pageable paramPageable);

	Users findFirstByOrderByAgeAsc();

	@Transactional(timeout = 10)
	@Query("select u from Users u where u.passWord = ?1")
	List<Users> myFindByPassWord(String passWord,Date date);

	@Transactional
	@Modifying
	@Query("update Users u set u.age = ?1 where u.id= ?2")
	void myUpdateAgeById(int paramInt, long paramLong);


	@Query("select u as users,a as address,u.id as uid,u.userName as userName,u.age as age," +
			"a.id as aid,a.province as province,a.city as city,a.area as area " +
			"from Users u inner join Address a on a.usersid = u.id where u.id = ?1")
	UsersInfo queryUsersInfoByUid(long i);

	@Query("select u as users,a as address,u.id as uid,u.userName as userName,u.age as age," +
			"a.id as aid,a.province as province,a.city as city,a.area as area " +
			"from Users u inner join Address a on a.usersid = u.id where u.id = ?1")
	Page<UsersInfo> queryUsersInfosByUid(long i, Pageable pageable);
}