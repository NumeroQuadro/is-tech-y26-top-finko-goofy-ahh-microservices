package srfc.Controllers;

import WrappedModels.CatMainInfoDto;
import WrappedModels.OwnerWithCatDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import src.Services.CatService;
import src.Services.OwnerService;
import src.Services.UserService;

@RestController
@RequestMapping("/cats_tracking/owners")
public class OwnerRestController {
    private final Logger logger = LoggerFactory.getLogger(OwnerRestController.class);
    private final UserService userService;
    private final OwnerService ownerService;
    private final CatService catService;
    @Autowired
    public OwnerRestController(CatService catService, OwnerService ownerService, UserService userService) {
        this.userService = userService;
        this.ownerService = ownerService;
        this.catService = catService;
    }

    @PostMapping("/add-cat")
    public ResponseEntity<String> addNewCatAssociatedWithOwner(@RequestBody CatMainInfoDto catDto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var ownerName = authentication.getName();

        try {
            var user = userService.getUserByNameOrNull(ownerName);
            if (user == null) {
                return new ResponseEntity<>("There are no such user with name " + ownerName, HttpStatus.NO_CONTENT);
            }

            var catId = catService.createNewCat(catDto);
            ownerService.addCatAssociatedToOwner(new OwnerWithCatDto(user.getId(), catId));

            return ResponseEntity.ok("Cat added successfully");
        }
        catch (Exception e) {
            logger.error("Unable to associate cat to owner due to: " + e.getMessage());

            return new ResponseEntity<>("Unable to associate cat to owner", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/fuck")
    public String fuck() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
