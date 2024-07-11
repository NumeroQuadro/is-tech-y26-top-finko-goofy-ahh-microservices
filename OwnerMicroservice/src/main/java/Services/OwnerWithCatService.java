package Services;

import Models.OwnerWithCats;
import Models.OwnerWithCatsEmbedded;
import Repositories.OwnerWithCatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerWithCatService {
    private final OwnerWithCatsRepository ownerWithCatsRepository;

    @Autowired
    public OwnerWithCatService(OwnerWithCatsRepository ownerWithCatsRepository) {
        this.ownerWithCatsRepository = ownerWithCatsRepository;
    }

    public OwnerWithCats getByIdOrNull(Integer userId, Integer catId) {
        return ownerWithCatsRepository.findById(new OwnerWithCatsEmbedded(userId, catId)).orElse(null);
    }

    public void addNewOwnerWithCat(Integer userId, Integer catId) {
        ownerWithCatsRepository.save(new OwnerWithCats(userId, catId));
    }

    public List<OwnerWithCats> getAllCatsAssociatedWithOwnerById(Integer userId) {
        return ownerWithCatsRepository.findAllByIdUserId(userId);

    }
}
