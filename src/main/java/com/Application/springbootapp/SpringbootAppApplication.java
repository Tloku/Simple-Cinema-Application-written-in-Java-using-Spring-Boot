package com.Application.springbootapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public abstract class SpringbootAppApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringbootAppApplication.class)
				.headless(false)
				.run(args);
	}
}
