/*
 * package com.java.space.dto.util;
 * 
 * import java.io.IOException; import java.util.Arrays;
 * 
 * import
 * org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
 * import org.springframework.boot.jackson.JsonComponent;
 * 
 * import com.fasterxml.jackson.core.JsonParser; import
 * com.fasterxml.jackson.core.JsonProcessingException; import
 * com.fasterxml.jackson.databind.DeserializationContext; import
 * com.fasterxml.jackson.databind.JsonDeserializer; import
 * com.java.space.dto.Property.Type;
 * 
 * @JsonComponent
 * 
 * @ConfigurationPropertiesBinding public class TypeDeserializer extends
 * JsonDeserializer<Type>{
 * 
 * @Override public Type deserialize(JsonParser p, DeserializationContext ctxt)
 * throws IOException, JsonProcessingException { return
 * Arrays.stream(Type.values()).filter(x-> { try { return
 * x.name().toLowerCase().equals(p.getValueAsString().toLowerCase()); } catch
 * (IOException e) { e.printStackTrace(); return false; }
 * }).peek(System.out::println).findFirst().get(); }
 * 
 * 
 * 
 * }
 */