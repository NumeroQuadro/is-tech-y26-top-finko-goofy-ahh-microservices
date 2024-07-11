package Repositories;

import Models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Owner findByName(String ownerName);
    boolean existsByName(String ownerName);
}
