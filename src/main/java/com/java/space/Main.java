
  package com.java.space;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
  public class Main implements WebMvcConfigurer{
	  public static void main(String[] args) {
		
		  SpringApplication.run(Main.class, args);
	}
	  
	/*
	 * @Bean public Module module(){ // register as module SimpleModule module = new
	 * SimpleModule(); module.addSerializer(Type.class, new TypeSerializer()); //
	 * 
	 * module.addDeserializer(Type.class, new TypeDeserializer()); return module; }
	 */
	 
	  
	/*
	 * @Override protected void
	 * extendMessageConverters(List<HttpMessageConverter<?>> converters) {
	 * MappingJackson2HttpMessageConverter converter =
	 * (MappingJackson2HttpMessageConverter) converters.stream() .filter(c -> c
	 * instanceof MappingJackson2HttpMessageConverter) .findFirst().get();
	 * Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
	 * JsonComponentModule module = new JsonComponentModule();
	 * module.addSerializer(Type.class, new TypeSerializer()); ObjectMapper
	 * objectMapper = builder.modules(module).build();
	 * converter.setObjectMapper(objectMapper); }
	 */
  }
