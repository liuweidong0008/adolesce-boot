package com.boot.module.users.entity;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity	//(name="users")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * PS:@GeneratedValue注解的strategy属性提供四种值:
	 *	-AUTO主键由程序控制, 是默认选项 ,不设置就是这个
	 *	-IDENTITY 主键由数据库生成, 采用数据库自增长, Oracle不支持这种方式
	 *	-SEQUENCE 通过数据库的序列产生主键, MYSQL  不支持
	 *	-Table 提供特定的数据库产生主键, 该方式更有利于数据库的移植
	 * SpringBoot的@GeneratedValue 是不需要加参数的,但是如果数据库控制主键自增(auto_increment), 不加参数就会报错.(血的教训, 看了@GeneratedValue源代码才知道)*/

	/**
	 * 标识主键
	 */
	@Id
	/**
	 * 主键策略
	 */
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String userName;

	/**
	 * 在不进行字段设置时没有此注解也行
	 * 		updatable = false  不会更新此字段，也不会报错,可以插入
	 * 		insertable = false 不会插入此字段，也不会报错，可更新
	 */
	@Column
	private String passWord;

	@Column
	private Integer age;
	
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;

	/**
	 * 不参与数据库映射
	 */
	@Transient
	private String otherParam;
	@Transient
	private String birthdayStr;
	@Transient
	private String startTime;
	@Transient
	private String endTime;

	public Users() {
	}

	public Users(Long id, String userName, String passWord, Integer age) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.age = age;
	}

	public String getBirthdayStr() {
		return DateUtil.format(this.birthday,"yyyy-MM-dd HH:mm:ss");
	}
}