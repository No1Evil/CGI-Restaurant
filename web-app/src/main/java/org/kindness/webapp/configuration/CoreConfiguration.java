package org.kindness.webapp.configuration;

import org.kindness.common.model.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {
    @Bean
    public JwtTokenProvider tokenProvider(){
        return new JwtTokenProvider();
    }
}
