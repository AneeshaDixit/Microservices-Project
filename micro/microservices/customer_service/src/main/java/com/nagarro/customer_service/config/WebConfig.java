package com.nagarro.customer_service.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestAdapter;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

//	@Bean
//	@LoadBalanced
//	public WebClient.Builder webClientBuilder() {
//	    return WebClient.builder();
//	}
//
//	@Bean
//	@LoadBalanced
//	public WebClient webClient(WebClient.Builder webClientBuilder) {
//	    return webClientBuilder.build();
//	}
//	
//	  @Bean
//	    @LoadBalanced
//	    public WebClient loadBalancedWebClient(WebClient.Builder webClientBuilder) {
//	        return webClientBuilder.build();
//	    }
	
	
	 @Bean
	    @LoadBalanced
	    public WebClient.Builder loadBalancedWebClientBuilder() {
	        return WebClient.builder();
	    }

	    @Bean
	    public WebClient.Builder webClientBuilder() {
	        return WebClient.builder();
	    }
	
	@Bean
	@LoadBalanced
	public WebClient.Builder deleteAccountWebClient() {
	    return WebClient.builder();
	}
	
}
