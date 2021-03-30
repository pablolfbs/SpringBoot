package com.pablo.profile.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ConfigProd implements Config {
	
	public ConfigProd() {
		System.out.println("Configurações de Prod...");
	}

}
