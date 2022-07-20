package com.droidlogic.neolink.server.config;

import com.droidlogic.neolink.server.NeolinkService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NeolinkConfig {

    @Bean
    public NeolinkService neolinkService() {
        return new NeolinkService();
    }

}
