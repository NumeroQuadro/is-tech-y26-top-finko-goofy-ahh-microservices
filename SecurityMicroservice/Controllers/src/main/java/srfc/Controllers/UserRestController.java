package srfc.Controllers;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.DtoModels.OwnerDto;
import src.Models.Role;
import src.Services.UserService;
import src.WrappedModels.OwnerMainInfoDto;
import src.WrappedModels.OwnerWithPassword;

@RestController
@RequestMapping("/cats_tracking/users")
public class UserRestController {
    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    private final UserService userService;
    private final RabbitTemplate template;

    @Autowired
    public UserRestController(UserService userService, RabbitTemplate template) {
        this.userService = userService;
        this.template = template;
    }

    @PostMapping("/add-user")
    public String addOwnerWithoutCats(@RequestBody OwnerWithPassword ownerWithPassword) {
//        userService.addNewUser(ownerWithPassword.getName(), ownerWithPassword.getPassword(), Role.USER);
//        var newOwner = new OwnerMainInfoDto(ownerWithPassword.getName(), ownerWithPassword.getBirthDate());
//        template.convertAndSend("addOwnerQueue", newOwner);
//
//        logger.info("Emit to addOwnerQueue");

        return "SLDKFJ";
//        return new ResponseEntity<>("Owner added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-user/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        var user = userService.findUserByName(username);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/fuck")
    public String fuck() {
        return "fuck";
    }
}
