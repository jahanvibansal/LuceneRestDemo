package com.java.space.dto.util;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;

import com.java.space.dto.Property.Type;

@Component
public class TypeConverter extends PropertyEditorSupport {
 
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(Type.valueOf(text.toUpperCase()));
	}
	
	@Override
	public String getAsText() {
		return ((Type)getValue()).name();
	}
}
