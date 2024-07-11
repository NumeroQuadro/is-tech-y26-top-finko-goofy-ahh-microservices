package src.Services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.security.user")
public class DefaultCredentialsHolder {
    private String name;
    private String password;
}