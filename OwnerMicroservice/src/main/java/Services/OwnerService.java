package Services;

import Models.Owner;
import Models.OwnerWithCats;
import Repositories.OwnerRepository;
import Repositories.OwnerWithCatsRepository;
import WrappedModels.OwnerMainInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private OwnerRepository ownerRepository;
    private OwnerWithCatsRepository ownerWithCatsRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, OwnerWithCatsRepository ownerWithCatsRepository) {
        this.ownerRepository = ownerRepository;
        this.ownerWithCatsRepository = ownerWithCatsRepository;
    }

    private final ModelMapper mapper = new ModelMapper();

    public void addOwnerWithoutCats(OwnerMainInfoDto ownerMainInfoDto) {
        var owner = mapper.map(ownerMainInfoDto, Owner.class);
        ownerRepository.save(owner);
    }

    public void findOwnerByName(String name) {
        var owner = ownerRepository.findByName(name);

        if (owner == null) {
            throw new IllegalStateException("Owner with name " + name + " not found");
        }
    }

    public Owner getOwnerByIdOrNull(Integer id) {
        return ownerRepository.findById(id).orElse(null);
    }

    public void updateOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public void addNewCatAssociatedWithOwner(String ownerName, Integer catId) {
        var owner = ownerRepository.findByName(ownerName);

        if (owner == null) {
            throw new IllegalStateException("Owner with name " + ownerName + " does not exists!");
        }

        var ownerWithCats = new OwnerWithCats(owner.getId(), catId);
        ownerWithCatsRepository.save(ownerWithCats);
    }

    public void getCatIdsAssociatedWithOwner(String ownerName) {
        var owner = ownerRepository.findByName(ownerName);

        if (owner == null) {
            throw new IllegalStateException("Owner with name " + ownerName + " does not exists!");
        }

        var ownerWithCats = new OwnerWithCats(owner.getId(), null);
        ownerWithCatsRepository.findAll(Example.of(ownerWithCats));

    }

    public Owner getByIdOrNull(Integer id) {
        return ownerRepository.findById(id).orElse(null);
    }
}
