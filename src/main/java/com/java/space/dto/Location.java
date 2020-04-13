package com.java.space.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location implements Serializable{
	boolean hideHouseNumberFromBuyers;
	String houseNumber;
	String locality;
	String projectName;
	String state;
	String city;
	int pincode;
	String country;

}