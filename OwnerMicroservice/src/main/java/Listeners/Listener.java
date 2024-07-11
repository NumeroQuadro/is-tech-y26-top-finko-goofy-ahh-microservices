package Listeners;

import Services.OwnerService;
import Services.OwnerWithCatService;


import WrappedModels.CatIdsDto;
import WrappedModels.OwnerMainInfoDto;
import WrappedModels.OwnerWithCatDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@EnableRabbit
@Component
public class Listener {
    Logger logger = LoggerFactory.getLogger(Listener.class);
    private final OwnerService ownerService;
    private final OwnerWithCatService ownerWithCatService;

    @Autowired
    public Listener(OwnerService ownerService, OwnerWithCatService ownerWithCatService) {
        this.ownerService = ownerService;
        this.ownerWithCatService = ownerWithCatService;
    }

    @RabbitListener(queues = RabbitConfiguration.addOwnerQueueName)
    @SendTo
    public boolean processAddOwnerQueue(OwnerMainInfoDto ownerMainInfoDto) {
        try {
            logger.info("Received ownerDto object to addOwnerQueue: " + ownerMainInfoDto);
            ownerService.addOwnerWithoutCats(ownerMainInfoDto);

            return true;
        }
        catch (Exception e) {
            logger.error("Error while processing message: " + e);

            return false;
        }
    }

    @RabbitListener(queues = RabbitConfiguration.addCatToOwnerQueueName)
    @SendTo
    public boolean processAddCatToOwnerQueue(OwnerWithCatDto ownerWithCatDto) {
        var owner = ownerService.getByIdOrNull(ownerWithCatDto.getUserId());
        if (owner == null) {
            logger.error("Unable to associate new cat to owner, there are no owner: ");

            return false;
        }

        ownerWithCatService.addNewOwnerWithCat(ownerWithCatDto.getUserId(), ownerWithCatDto.getCatId());

        return true;
    }

    @RabbitListener(queues = RabbitConfiguration.getCatIdsAssociatedWithOwnerName)
    @SendTo
    public CatIdsDto processGetCatsByColorQueueName(Integer ownerId) {
        var owner = ownerService.getOwnerByIdOrNull(ownerId);
        if (owner == null) {
            logger.error("There are no owners with id: " + ownerId);

            return new CatIdsDto(false, null);
        }

        var ownerWithCatList = ownerWithCatService.getAllCatsAssociatedWithOwnerById(ownerId);

        List<Integer> catIds = ownerWithCatList.stream()
                .map(value -> value.getId().getCatId())
                .toList();

        return new CatIdsDto(true, catIds);
    }
}