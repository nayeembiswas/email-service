package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 6, 2022
 * @version   1.0.0
 */

@Data
@Entity
@Table(name = "EMAIL_RECIPIENTS")
public class EmailRecipients implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "EMAILS_ID")
	private Emails emails;
	
	@Column(name = "EMAIL_ADDR" , length = 150)
	private String emailAddr;
	
	@Column(name = "IS_SENT", columnDefinition = "boolean default false")
	private Boolean isSent;
	
	@Column(name = "is_resent", columnDefinition = "boolean default false")
	private Boolean isResent;
	
	
	@Column(columnDefinition = "varchar(100)", nullable = false, updatable = false)
	private UUID createdBy = UUID.randomUUID();

//	@Column(nullable = false, updatable = false)
	@CreatedDate
	@JsonIgnore
	private LocalDateTime createdOn;

	@Column(columnDefinition = "varchar(100)")
	private UUID updatedBy;

	@LastModifiedDate
	@JsonIgnore
	private LocalDateTime updatedOn;

	@Column(columnDefinition = "boolean default true")
	private Boolean isDeleted;
	

}
