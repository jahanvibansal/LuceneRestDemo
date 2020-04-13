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
public class PropertyDetails implements Serializable{
	AreaType type;
	float minArea;
	float maxArea;
	AreaUnit unit;
	int noOfWashrooms;
	Availability availabiltiy;
	Age propertyAge;
	public enum Availability{
		READY_TO_MOVE, UNDER_CONSTRUCTION;
	}
	public enum AreaType{
		BUILTUP, CARPET, PLOT_SIZE
	}
	public enum AreaUnit{
		SQUARE_FEET, YARDS, SQUARE_METER, ACRES,MARLA, CENTS, BIGHA,KOTTHA, KANAL, GROUNDS, HECTARES
	}
	public enum Age{
		ZERO_TO_ONE, ONE_TO_FIVE, FIVE_TO_TEN, TEN_PLUS;
	}
}