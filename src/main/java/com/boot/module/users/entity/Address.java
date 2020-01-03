package com.boot.module.users.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	/**
	 * 主键策略
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long usersid;

	private String province;

	private String city;

	private String area;

	public Address() {
	}

	public Address(String province, String city, String area) {
		this.province = province;
		this.city = city;
		this.area = area;
	}
}