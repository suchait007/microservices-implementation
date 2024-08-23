package com.retail.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import reactor.core.publisher.Hooks;


@EnableAspectJAutoProxy
@EnableRetry
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
