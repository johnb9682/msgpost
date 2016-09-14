package com.msgpost.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects;
import com.msgpost.model.sys.param.UserParameter;

import core.support.DateTimeSerializer;

/**
 * Message的实体类
 * @Author: Bi Ran
 */
@Entity
@Table(name = "message")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Message extends UserParameter {

	// 各个字段的含义请查阅文档的数据库结构部分
	private static final long serialVersionUID = -2847988368314678488L;
	@Id
	@GeneratedValue
	@Column(name = "messageid", nullable = false, unique = true)
	private Integer messageid;
	@Column(name = "message", length = 255)
	private String message;
	@Column(name = "authorid")
	private Integer authorid; 
	@Column(name = "time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	public Message() {

	}

	public Integer getMessageid() {
		return messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid = messageid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Integer getAuthorid() {
		return authorid;
	}

	public void setAuthorid(Integer authorid) {
		this.authorid = authorid;
	}
	
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
/*
	public String getUserValue() {
		return UserValue;
	}

	public void setUserValue(String UserValue) {
		this.UserValue = UserValue;
	}

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
		final Message other = (Message) obj;
		return Objects.equal(this.messageid, other.messageid) && Objects.equal(this.authorid, other.authorid);
	}

	public int hashCode() {
		return Objects.hashCode(this.messageid, this.message, this.authorid);
	} 
}
