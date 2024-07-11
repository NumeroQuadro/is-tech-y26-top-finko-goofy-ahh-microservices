package Listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.amqp.rabbit.core.RabbitAdmin.QUEUE_NAME;

@Configuration
@Getter
public class RabbitConfiguration {
    public final static String addCatToOwnerQueueName = "addCatToOwnerQueue";
    public final static String addOwnerQueueName = "addOwnerQueue";
    public final static String getCatIdsAssociatedWithOwnerName = "getCatIdsAssociatedWithOwner";

    private final Logger logger = LoggerFactory.getLogger(RabbitConfiguration.class);

    @Bean
    public Queue addOwnerQueue() {
        return new Queue(addOwnerQueueName);
    }

    @Bean
    public Queue addCatToOwnerQueue() {
        return new Queue(addCatToOwnerQueueName);
    }

    @Bean
    public Queue getCatIdsAssociatedWithOwner() {
        return new Queue(getCatIdsAssociatedWithOwnerName);
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("WrappedModels.CatColor", WrappedModels.CatColor.class);
        idClassMapping.put("WrappedModels.CatDto", WrappedModels.CatDto.class);
        idClassMapping.put("WrappedModels.CatIdsDto", WrappedModels.CatIdsDto.class);
        idClassMapping.put("WrappedModels.CatMainInfoDto", WrappedModels.CatMainInfoDto.class);
        idClassMapping.put("WrappedModels.CatMainInfoListDto", WrappedModels.CatMainInfoListDto.class);
        idClassMapping.put("WrappedModels.OwnerIdCatColorDto", WrappedModels.OwnerIdCatColorDto.class);
        idClassMapping.put("WrappedModels.OwnerMainInfoDto", WrappedModels.OwnerMainInfoDto.class);
        idClassMapping.put("WrappedModels.OwnerWithCatDto", WrappedModels.OwnerWithCatDto.class);
        idClassMapping.put("WrappedModels.OwnerWithPassword", WrappedModels.OwnerWithPassword.class);
        idClassMapping.put("WrappedModels.UserDto", WrappedModels.UserDto.class);
        idClassMapping.put("WrappedModels.CatWithFriendIdsDto", WrappedModels.CatWithFriendIdsDto.class);
        typeMapper.setIdClassMapping(idClassMapping);
        converter.setJavaTypeMapper(typeMapper);

        return converter;
    }
}
