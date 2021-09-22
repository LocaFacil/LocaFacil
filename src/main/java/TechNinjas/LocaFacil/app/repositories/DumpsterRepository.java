package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Dumpster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DumpsterRepository extends JpaRepository <Dumpster, Integer>{
    Optional<Dumpster> findById(Integer id);
}
