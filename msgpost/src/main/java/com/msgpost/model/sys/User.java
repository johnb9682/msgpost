package com.msgpost.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects;
import com.msgpost.model.sys.param.UserParameter;

/**
 * 字典的实体类
 * @Author: Bi Ran
 */
@Entity
@Table(name = "user")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class User extends UserParameter {

	// 各个字段的含义请查阅文档的数据库结构部分
	private static final long serialVersionUID = -2847988368314678488L;
	@Id
	@GeneratedValue
	@Column(name = "userid", nullable = false, unique = true)
	private Long userid;
	@Column(name = "username", length = 45)
	private String username;
	@Column(name = "password", length = 100, nullable = false)
	private String password; 
	@Column(name = "imgpath", length = 1000)
	private String imgpath;
	/*
	@Column(name = "sequence")
	private Long sequence;
	@Column(name = "parent_Userkey", length = 20)
	private String parentUserkey;
*/
	public User() {

	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String Username) {
		this.username = Username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	/*
	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getParentUserkey() {
		return parentUserkey;
	}

	public void setParentUserkey(String parentUserkey) {
		this.parentUserkey = parentUserkey;
	}
*/
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		return Objects.equal(this.userid, other.userid) && Objects.equal(this.username, other.username);
	}

	public int hashCode() {
		return Objects.hashCode(this.userid, this.username, this.password);
	} 
}
