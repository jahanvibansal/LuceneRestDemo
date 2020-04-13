/*
 * package com.java.space.dto.util;
 * 
 * import java.io.IOException;
 * 
 * import
 * org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
 * import org.springframework.boot.jackson.JsonComponent;
 * 
 * import com.fasterxml.jackson.core.JsonGenerator; import
 * com.fasterxml.jackson.databind.JsonSerializer; import
 * com.fasterxml.jackson.databind.SerializerProvider; import
 * com.java.space.dto.Property.Type;
 * 
 * @JsonComponent
 * 
 * @ConfigurationPropertiesBinding public class TypeSerializer extends
 * JsonSerializer<Type> {
 * 
 * @Override public void serialize(Type value, JsonGenerator gen,
 * SerializerProvider serializers) throws IOException {
 * gen.writeString(value.name()); }
 * 
 * }
 */