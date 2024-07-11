package srfc.Controllers;

import WrappedModels.CatColor;
import WrappedModels.CatDto;
import WrappedModels.CatMainInfoDto;
import WrappedModels.OwnerIdCatColorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import src.Listener.RabbitConfiguration;
import src.Repositories.UserRepository;
import src.Services.CatService;
import src.Services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cats_tracking/cats")
public class CatRestController {
    private final Logger logger = LoggerFactory.getLogger(OwnerRestController.class);
    private final UserService userService;
    private final CatService catService;

    @Autowired
    public CatRestController(CatService catService, UserService userService) {
        this.userService = userService;
        this.catService = catService;
    }

    @PostMapping("/add-cat")
    public ResponseEntity<String> addNewCatAssociatedWithOwner(@RequestBody CatMainInfoDto catDto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var ownerName = authentication.getName();

        try {
            var user = userService.getUserByNameOrNull(ownerName);
            if (user == null) {
                return new ResponseEntity<>("There are no such user!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            catService.createNewCat(catDto);

            return new ResponseEntity<>("Cat created successfully", HttpStatus.OK);
        }

        catch (Exception e) {
            logger.error("Unable to create cat due to: " + e.getMessage());

            return new ResponseEntity<>("Unable to create new cat", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-cats-by-color/{color}")
    public ResponseEntity<List<CatDto>> getCatsByColorAssociatedWithOwner(@PathVariable String color) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var ownerName = authentication.getName();

        try {
            var user = userService.getUserByNameOrNull(ownerName);
            if (user == null) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(catService.getAllCatsAssociatedToOwnerByColor(new OwnerIdCatColorDto(user.getId(), CatColor.valueOf(color))), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add-friend/{targetCatId}/{friendCatId}")
    public ResponseEntity<String> addFriendToExistedCat(@PathVariable Integer targetCatId, @PathVariable Integer friendCatId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var ownerName = authentication.getName();

        try {
            var user = userService.getUserByNameOrNull(ownerName);
            if (user == null) {
                return new ResponseEntity<>("There are no such user with username " + ownerName, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            catService.addFriendToExistingCat(targetCatId, friendCatId);

            return new ResponseEntity<>("Friend added successfully!", HttpStatus.OK);
        }

        catch (Exception e) {
            return new ResponseEntity<>("Unable to add friend to cat due to: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fuck")
    public String fuck() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
