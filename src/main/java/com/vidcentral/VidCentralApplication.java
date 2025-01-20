package com.vidcentral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.vidcentral.global.config.TokenConfig;

@EnableJpaAuditing
@EnableConfigurationProperties(TokenConfig.class)
@SpringBootApplication
public class VidCentralApplication {

	public static void main(String[] args) {
		SpringApplication.run(VidCentralApplication.class, args);
	}

}
