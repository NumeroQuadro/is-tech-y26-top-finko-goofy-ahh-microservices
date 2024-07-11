package src.Services;

import WrappedModels.OwnerWithCatDto;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Listener.RabbitConfiguration;
import src.Repositories.UserRepository;


@Service
public class OwnerService {
    private final Logger logger = LoggerFactory.getLogger(OwnerService.class);
    private final UserRepository userRepository;
    private final RabbitTemplate template;

    @Autowired
    public OwnerService(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.template = rabbitTemplate;
    }

    public void addCatAssociatedToOwner(OwnerWithCatDto ownerWithCatDto) {
        try {
            var result = (Boolean) template.convertSendAndReceive(RabbitConfiguration.addCatToOwnerQueueName, ownerWithCatDto);
        }
        catch (Exception e) {
            logger.error("OwnerService unable to add cat due to: " + e.getMessage());

            throw e;
        }
    }
}
