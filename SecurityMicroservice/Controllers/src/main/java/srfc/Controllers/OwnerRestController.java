package srfc.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import src.DtoModels.CatDto;
import src.DtoModels.OwnerDto;
import src.Models.Role;
import src.Repositories.UserRepository;

@RestController
@RequestMapping("/cats_tracking/owners")
public class OwnerRestController {
    private final Logger logger = LoggerFactory.getLogger(OwnerRestController.class);
    private final UserRepository userRepository;
    private final RabbitTemplate template;

    @Autowired
    public OwnerRestController(UserRepository userRepository, RabbitTemplate template) {
        this.userRepository = userRepository;
        this.template = template;
    }

    @PostMapping("/add-cat")
    public ResponseEntity<String> addNewCatAssociatedWithOwner(@RequestBody CatDto catDto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var ownerName = authentication.getName();

        var existingUser = userRepository.findByUsername(ownerName).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        template.convertAndSend("addCatQueue", catDto);

        return ResponseEntity.ok("Cat added successfully");
    }

    @GetMapping("/fuck")
    public String fuck() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
