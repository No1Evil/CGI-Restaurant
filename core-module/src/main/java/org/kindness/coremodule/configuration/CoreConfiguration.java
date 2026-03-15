package org.kindness.coremodule.configuration;

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
