package com.nschlimm.msrefgateway;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class MsrefGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsrefGatewayApplication.class, args);
	}
	
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p
                .path("/schlimmbank/ACCOUNTSMS/**")
                .filters(f -> f.rewritePath("/schlimmbank/ACCOUNTSMS/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString()))
                .uri("lb://ACCOUNTSMS")).
            route(p -> p
                    .path("/schlimmbank/loans/**")
                    .filters(f -> f.rewritePath("/schlimmbank/loans/(?<segment>.*)","/${segment}")
                            .addResponseHeader("X-Response-Time",new Date().toString()))
                    .uri("lb://LOANS")).
            route(p -> p
                    .path("/schlimmbank/cards/**")
                    .filters(f -> f.rewritePath("/schlimmbank/cards/(?<segment>.*)","/${segment}")
                            .addResponseHeader("X-Response-Time",new Date().toString()))
                    .uri("lb://CARDS")).build();
    }
}
