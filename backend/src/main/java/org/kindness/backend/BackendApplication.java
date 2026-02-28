package org.kindness.backend;

import org.kindness.api.dao.BaseDao;
import org.kindness.api.model.Author;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BackendApplication {

    static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataAccessCheck(BaseDao<Author> dao){
        return args -> {
            Author author = new Author("deva", "be", LocalDate.MAX);
            Author author1 = new Author("dev", "ba", LocalDate.MAX);
            Author author2 = new Author("de", "bc", LocalDate.MAX);

            dao.insert(author);
            dao.insert(author1);
            dao.insert(author2);

            var authors = dao.findAll();
            authors.forEach(s -> {
                System.out.println(s.getFirstName());
            });

            dao.deleteById(1L);
            System.out.println(dao.findById(1L));

            var authors1 = dao.findAll();
            authors1.forEach(s -> {
                System.out.println(s.getFirstName());
            });
        };
    }
}
