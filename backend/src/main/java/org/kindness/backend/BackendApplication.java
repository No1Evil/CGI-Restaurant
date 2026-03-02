package org.kindness.backend;

import org.kindness.common.dao.BaseDao;
import org.kindness.common.model.impl.User;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataAccessCheck(BaseDao<User> dao){
        return args -> {

        };
    }
}
