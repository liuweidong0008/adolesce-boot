package com.boot.module.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lwd
 * @since 2018-03-02
 */
@Data
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
