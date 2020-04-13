package com.java.space.dto;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Property implements Serializable{
	private static final long serialVersionUID = -1352390247202313343L;
	String id= UUID.randomUUID().toString();
	User user;
	String title;
	String description;
	LocalDate availableFrom;
	Location location;
	int popularity;
	Purpose purpose;
	Type type;
	boolean multipleUnits;
	int noOfProperties;
	PropertyDetails details;
	Pricing pricing;
	List<File> pictures;
	List<String> amenities;
	Timings timing;
	Category category;
	public enum User{
		OWNER, DEALER, BUILDER
	}
	public enum Purpose{
		RENT,LEASE, SALE
	}
	public enum Type{
		ROOM, SEAT, GYM, COMMERCIAL;
		/*
		 * @JsonValue public String getValue() { return this.name(); }
		 */
	}
	public enum Timings{
		HOURLY, DAILY, MONTHLY
	}
	public enum Category{
		PREMIUM, NORMAL
	}
}
