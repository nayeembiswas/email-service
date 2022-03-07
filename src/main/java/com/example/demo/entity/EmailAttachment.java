/**
 * 
 */
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
@Table(name = "EMAIL_ATTACHMENT")
public class EmailAttachment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "EMAILS_ID")
	private Emails emails;
	
	@Column(name = "type" , length = 20)
	private String type;
	
	@Column(name = "FILE_URL")
	private String fileUrl;

}
