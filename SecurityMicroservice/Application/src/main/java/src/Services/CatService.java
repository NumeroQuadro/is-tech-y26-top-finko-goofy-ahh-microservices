package src.Services;

import WrappedModels.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Listener.RabbitConfiguration;
import src.Repositories.UserRepository;
import java.awt.*;
import java.lang.module.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {
    private final Logger logger = LoggerFactory.getLogger(CatService.class);
    private final UserRepository userRepository;
    private final RabbitTemplate template;

    @Autowired
    public CatService(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.template = rabbitTemplate;
    }

    public Integer createNewCat(CatMainInfoDto catDto) {
        var catId = (Integer) template.convertSendAndReceive(RabbitConfiguration.registerNewCatQueueName, catDto);
        if (catId == null || catId <= 0) {
            throw new IllegalArgumentException("Unable to create new cat");
        }

        return catId;
    }

    public List<CatDto> getAllCatsAssociatedToOwnerByColor(OwnerIdCatColorDto ownerIdCatColorDto) {
        try {
            var object = template.convertSendAndReceive(
                    RabbitConfiguration.getCatIdsAssociatedWithOwnerName,
                    ownerIdCatColorDto.getOwnerId()
            );
            var catIdsDto = (CatIdsDto) object;

            if(catIdsDto == null || !catIdsDto.getIsSuccess()) {
                throw new IllegalArgumentException("Unable to get list of cat ids, list of cat ids is null");
            }

            var catInfosDto = (CatMainInfoListDto) template.convertSendAndReceive(
                    RabbitConfiguration.getCatDetailedInfoByListIdsAndColorName,
                    catIdsDto
            );
            if (catInfosDto == null || !catInfosDto.getIsSuccess()) {
                throw new IllegalArgumentException("Unable to get list of cats, list is null");
            }

            return catInfosDto
                    .getDtos()
                    .stream()
                    .filter(catDto -> catDto
                            .getColor()
                            .equals(ownerIdCatColorDto.getCatColor()))
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error("Unable to get cats by color due to: " + e.getMessage());

            throw new IllegalArgumentException("Unable to get cats by color due to: " + e);
        }
    }

    public void addFriendToExistingCat(Integer targerCatId, Integer friendCatId) {
        try {
            var result = (Boolean) template.convertSendAndReceive(RabbitConfiguration.addFriendToCatQueueName, new CatWithFriendIdsDto(targerCatId, friendCatId));
            if (result == null || !result) {
                logger.error("There are an issue occured while adding friend to cat");

                throw new IllegalArgumentException("There are an issue occured while adding friend to cat");
            }
        }
        catch (Exception e) {
            logger.error("There are an issue occured while adding friend to cat " + e);

            throw new IllegalArgumentException("There are an issue occured while adding friend to cat");
        }
    }
}
