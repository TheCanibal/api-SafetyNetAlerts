package com.APISafetyNetAlerts.apiForSNA.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("actuator-endpoints")
//if you want: register bean only if profile is set
public class HttpTraceConfiguration {

    @Bean
    public HttpExchangeRepository httpTraceRepository() {
	return new InMemoryHttpExchangeRepository();
    }
}
