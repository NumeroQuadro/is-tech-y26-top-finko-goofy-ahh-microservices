package Repositories;

import Models.OwnerWithCats;
import Models.OwnerWithCatsEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnerWithCatsRepository extends JpaRepository<OwnerWithCats, OwnerWithCatsEmbedded> {
    List<OwnerWithCats> findAllByIdUserId(Integer userId);
}
