package com.boot.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author lwd
 * @since 2018-03-02
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

	private String id;
	private Integer age;
	@TableField("birth_day")
	private Date birthDay;
	private String email;
	private Long flag;
	private String hobby;
	private String name;
	private String password;
	private String phone;
	private String role;
	private String salt;
	private BigDecimal saraly;
	private String sex;
	private String usernum;
	private Boolean checked;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public BigDecimal getSaraly() {
		return saraly;
	}

	public void setSaraly(BigDecimal saraly) {
		this.saraly = saraly;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsernum() {
		return usernum;
	}

	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", birthDay=" + birthDay + ", email=" + email + ", flag=" + flag
				+ ", hobby=" + hobby + ", name=" + name + ", password=" + password + ", phone=" + phone + ", role="
				+ role + ", salt=" + salt + ", saraly=" + saraly + ", sex=" + sex + ", usernum=" + usernum + "]";
	}
	
}
