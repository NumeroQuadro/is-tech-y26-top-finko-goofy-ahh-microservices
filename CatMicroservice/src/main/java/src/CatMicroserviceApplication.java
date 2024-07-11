package src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"src.Repositories"})
@ComponentScan(basePackages = {"src.Listeners", "src.Services"})
@EntityScan(basePackages = {"src.Models"})
public class CatMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatMicroserviceApplication.class, args);
    }
}
