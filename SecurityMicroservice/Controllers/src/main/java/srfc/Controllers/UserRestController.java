package srfc.Controllers;

import WrappedModels.OwnerWithPassword;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import src.Models.Role;
import src.Services.UserService;

@RestController
@RequestMapping("/cats_tracking/users")
public class UserRestController {
    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    private final UserService userService;
    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addOwnerWithoutCats(@RequestBody OwnerWithPassword ownerWithPassword) {
        try {
            userService.addNewUser(ownerWithPassword.getName(), ownerWithPassword.getPassword(), ownerWithPassword.getBirthDate(), Role.USER);
        }
        catch (Exception e) {
            logger.error("Unable to add user due to: " + e);

            return new ResponseEntity<>("An error was occured while pushing message into queue", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Emit to addOwnerQueue");

        return new ResponseEntity<>("Owner added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-user/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        var user = userService.getUserByNameOrNull(username);
        if (user == null) {
            return new ResponseEntity<>("There are no such user!", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/fuck")
    public String fuck() {
        return "fuck";
    }
}
