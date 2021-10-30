package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Dumpster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DumpsterRepository extends JpaRepository <Dumpster, Integer>{
    Optional<Dumpster> findById(Integer id);

    //SELECT * FROM tb_dumpster INNER JOIN status ON tb_dumpster.id = status.tb_dumpster_id AND status.status = 1
    //SELECT * FROM tb_dumpster INNER JOIN status ON tb_dumpster.id = status.tb_dumpster_id AND status.status = 1

    //select * from tb_dumpster, status where tb_dumpster.id = status.tb_dumpster_id AND status.status = 3
    // AND status.tb_dumpster_id = 5
}
