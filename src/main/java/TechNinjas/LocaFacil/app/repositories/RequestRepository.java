package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    Optional<Request> findById(Integer id);
    List<Request> findAllByDumpster(Dumpster dumpster);
    List<Request> findAllByClient(Client client);

    @Query("select d from Tb_Request d where d.clientidois=:p")
    List<Request> findAllClientById(@Param("p") Integer id);
}
