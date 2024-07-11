package org.owners.securitymicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import src.Services.UserService;

@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackages = {"src.Repositories"})
@ComponentScan(basePackages = {"srfc.Controllers", "src.Listener", "src.Services", "srfc.Security", "org.owners.securitymicroservice"})
@EntityScan(basePackages = {"src.Models"})
public class SecurityMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecurityMicroserviceApplication.class, args);
	}
}
