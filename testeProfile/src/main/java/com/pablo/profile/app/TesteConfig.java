package com.pablo.profile.app;

import java.io.ObjectInputFilter.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TesteConfig {
	
	@Autowired
	public TesteConfig(Config config) {
		System.out.println("Config classs = " + config.getClass().getName());
	}

}
