package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    Optional<Request> findById(Integer id);
}
