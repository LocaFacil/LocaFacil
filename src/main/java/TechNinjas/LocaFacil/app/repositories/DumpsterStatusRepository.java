package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Dumpster;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DumpsterStatusRepository {
    private final EntityManager em;

    public DumpsterStatusRepository(EntityManager em) {
        this.em = em;
    }

    public List<Dumpster> find(Integer tb_dumpster_id/*, Integer status*/) {

        //select c, p from tb_dumpster c INNER JOIN status p ON c.id = p.tb_dumpster_id WHERE p.status = 3 AND p.tb_dumpster_id =5

        String query = "select C from Tb_Dumpster C, Status P ";
        String condition = "where ";
        String query2 = "C.Status = P ";
        //String query3 = "on C.id = P.tb_dumpster_id ";
        String condition2 = "and ";

        if(tb_dumpster_id != null) {
            query += condition += query2/* += query3 */+= condition2 + " P.tb_dumpster_id = :tb_dumpster_id";
            //condition = " and ";
        }

//        if(status != null) {
//            query += condicao + " P.status = :status";
//        }

        var q = em.createQuery(query, Dumpster.class);


        if(tb_dumpster_id != null) {
            q.setParameter("tb_dumpster_id", tb_dumpster_id);
        }

//        if(status != null) {
//            q.setParameter("status", status);
//        }

        return q.getResultList();
    }
}
