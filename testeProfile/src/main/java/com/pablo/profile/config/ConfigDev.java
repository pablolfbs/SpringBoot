package com.pablo.profile.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ConfigDev implements Config {
	
	public ConfigDev() {
		System.out.println("Configurações de Dev...");
	}

}
