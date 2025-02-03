package com.vidcentral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.vidcentral.global.config.TokenConfig;

@EnableJpaAuditing
@EnableConfigurationProperties(TokenConfig.class)
@EnableAsync
@SpringBootApplication
public class VidCentralApplication {

	public static void main(String[] args) {
		SpringApplication.run(VidCentralApplication.class, args);
	}

}
