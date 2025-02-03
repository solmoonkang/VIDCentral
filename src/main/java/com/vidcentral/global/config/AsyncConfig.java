package com.vidcentral.global.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

	@Bean(name = "taskExecutor")
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();

		threadPoolExecutor.setCorePoolSize(5);
		threadPoolExecutor.setQueueCapacity(20);
		threadPoolExecutor.setMaxPoolSize(10);
		threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		return threadPoolExecutor;
	}
}
