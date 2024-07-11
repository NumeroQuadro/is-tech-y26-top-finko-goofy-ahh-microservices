package src.Services;



import WrappedModels.OwnerMainInfoDto;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.query.IllegalQueryOperationException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import src.Models.Role;
import src.Models.Users;
import src.Repositories.UserRepository;

import java.time.LocalDate;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RabbitTemplate template;

    @Autowired
    public UserService(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.template = rabbitTemplate;
    }

    @Transactional
    public void addAdminIfNotExists(String username, String password) {
        var adminExists = userRepository.existsByUsername(username);
        if (!adminExists) {
            addNewUser(username, password, LocalDate.now(), Role.ADMIN);
        }
    }

    @Transactional
    public void addNewUser(String username, String password, LocalDate birthDate, Role role) {
        var existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new IllegalStateException("User with username " + username + " already exists");
        }
        var passwordEncoder = new BCryptPasswordEncoder();
        var encodedPassword = passwordEncoder.encode(password);
        var user = new Users(username, encodedPassword, role);
        var addedUser = userRepository.save(user);

        var newOwner = new OwnerMainInfoDto(addedUser.getId(), username, birthDate);

        var result = (Boolean) template.convertSendAndReceive("addOwnerQueue", newOwner);
        if (result == null) {
            throw new IllegalStateException("Result of adding admin is null. Unable to sure of successful adding owner");
        }
        if (!result) {
            throw new IllegalStateException("Unable to add new user");
        }
    }

    public Users getUserByNameOrNull(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
