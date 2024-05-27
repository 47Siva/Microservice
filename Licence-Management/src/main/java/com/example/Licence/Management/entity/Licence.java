package com.example.Licence.Management.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.Licence.Management.common.ExpriedStatusDeserializer;
import com.example.Licence.Management.common.StatusDeserializer;
import com.example.Licence.Management.enumuration.ExpiredStatus;
import com.example.Licence.Management.enumuration.Status;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Licence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@GenericGenerator(name = "UUID2", type = org.hibernate.id.uuid.UuidGenerator.class)
	@JdbcTypeCode(SqlTypes.CHAR)
	private UUID id;

	private String companyName;

	private String companyAddress;

	private long contactNumber;

	private String mailId;

	private String gracePeriod;

	private String expiryDate;

	@JsonDeserialize(using = StatusDeserializer.class)
	@Enumerated(EnumType.STRING)
	private Status status;

	@JsonDeserialize(using = ExpriedStatusDeserializer.class)
	@Enumerated(EnumType.STRING)
	private ExpiredStatus expiredStatus;

	private String activeationDate;

	private String licenceKey;

}
