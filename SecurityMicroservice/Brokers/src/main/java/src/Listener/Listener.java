package src.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import src.Repositories.UserRepository;

@EnableRabbit
@Component
public class Listener {
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    private final UserRepository userRepository;

    @Autowired
    public Listener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
