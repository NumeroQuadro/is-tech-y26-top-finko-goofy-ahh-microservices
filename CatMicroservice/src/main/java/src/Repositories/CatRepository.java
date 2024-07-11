package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.Models.Cat;

import java.util.Optional;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {
    Optional<Cat> findByName(String name);
}
