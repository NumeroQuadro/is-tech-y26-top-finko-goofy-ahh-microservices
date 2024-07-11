package src.Listeners;

import WrappedModels.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import src.Services.CatService;

import java.util.ArrayList;

@EnableRabbit
@Component
public class Listener {
    private final ModelMapper modelMapper = new ModelMapper();
    Logger logger = LoggerFactory.getLogger(Listener.class);
    private final CatService catService;

    @Autowired
    public Listener(CatService catService) {
        this.catService = catService;
    }

    @RabbitListener(queues = RabbitConfiguration.registerNewCatQueueName)
    @SendTo
    public int processRegisterNewCatQueue(CatMainInfoDto catDto) {
        try {
            var addedCat = catService.createNewCat(catDto);
            return addedCat.getId();
        }
        catch (Exception e) {
            logger.error("Unable to create new cat: " + e.getMessage());

            return -1;
        }
    }

    @RabbitListener(queues = RabbitConfiguration.addFriendToCatQueueName)
    @SendTo
    public boolean processAddFriendToCatQueue(CatWithFriendIdsDto catWithFriendIdsDto) {
        try {
            catService.addFriendToTargetCat(catWithFriendIdsDto.getTargetCatId(), catWithFriendIdsDto.getFriendCatId());

            return true;
        }
        catch (Exception e) {
            logger.error("Unable to add friend with id " + catWithFriendIdsDto.getFriendCatId() + " to target cat with id " + catWithFriendIdsDto.getTargetCatId());

            return false;
        }
    }

    @RabbitListener(queues = RabbitConfiguration.getCatDetailedInfoByListIdsAndColorName)
    @SendTo
    public CatMainInfoListDto processGetCatsByColorQueue(CatIdsDto catIdsDto) {
        if (!catIdsDto.getIsSuccess()) {
            return new CatMainInfoListDto(false, null);
        }

        try {
             var catIds = catIdsDto.getCatIds();
             if (catIds == null) {
                 return new CatMainInfoListDto(false, new ArrayList<>());
             }

             // fixme: make it shorter with model mapper (I fuckin don't know how this shit (model mapper) works)
             var catDtosList = new ArrayList<CatDto>();
             for (var catId : catIds) {
                 var cat = catService.getCatByIdOrNull(catId);
                 if (cat == null) {
                     return new CatMainInfoListDto(false, new ArrayList<>());
                 }

                 var catDto = new CatDto(
                         cat.getId(),
                         cat.getName(),
                         cat.getBirthDate(),
                         cat.getBreed(),
                         cat.getColor()
                 );
                 catDtosList.add(catDto);
             }

             return new CatMainInfoListDto(true, catDtosList);
        }
        catch (Exception e) {
            logger.error("Unable to obtain detailed information about cats due to: " + e);

            return new CatMainInfoListDto(false, new ArrayList<>());
        }
    }
}
