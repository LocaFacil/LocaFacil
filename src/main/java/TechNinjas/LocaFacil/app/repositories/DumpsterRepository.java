package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Dumpster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DumpsterRepository extends JpaRepository <Dumpster, Integer>{
    Optional<Dumpster> findById(Integer id);

    @Query("select d from Tb_Dumpster d where d.statusid=:p")
    List<Dumpster> getDumpsterByStatus(@Param("p") Integer status);

    @Query("select d from Tb_Dumpster d where d.statusid=1")
    List<Dumpster> getDumpsterByStatusId();

    @Query("select d from Tb_Dumpster d where d.statusid=1 and d.size=2")
    List<Dumpster> getDumpsterByStatusIdAndSizeBig();

    @Query("select d from Tb_Dumpster d where d.statusid=1 and d.size=1")
    List<Dumpster> getDumpsterByStatusIdAndSizeSmall();
}
