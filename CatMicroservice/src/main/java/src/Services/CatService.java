package src.Services;

import WrappedModels.CatMainInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.Cat;
import src.Repositories.CatRepository;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class CatService {
    private CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Cat getCatByIdOrNull(Integer catId) {
        return catRepository.findById(catId).orElse(null);
    }

    public void updateCatInMainTable(Cat newCat) {
        catRepository.save(newCat);
    }
    public Cat createNewCat(CatMainInfoDto catDto) {
        var cat = new Cat();
        cat.setName(catDto.getName());
        cat.setBreed(catDto.getBreed());
        cat.setBirthDate(catDto.getBirthDate());
        cat.setColor(catDto.getColor());

        return catRepository.save(cat);
    }

    public Collection<Cat> listCatsFromMainTable() {
        return catRepository.findAll();
    }

    public void addFriendToTargetCat(Integer targetCatId, Integer friendCatId) {
        var targetCat = getCatByIdOrNull(targetCatId);
        var friendCat = getCatByIdOrNull(friendCatId);

        if (targetCat == null || friendCat == null) {
            throw new IllegalArgumentException("Unable to add friend cat with id " + friendCatId + " to cat with id " + targetCatId + " because of some of 2 cats is null (does not exist)");
        }

        targetCat.addFriend(friendCat);
        catRepository.save(targetCat);
        catRepository.save(friendCat);
    }
}
