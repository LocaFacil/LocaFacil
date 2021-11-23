package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Dumpster;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DumpsterCustomRepository {

    private final EntityManager em;

    public DumpsterCustomRepository(EntityManager em) {
        this.em = em;
    }

    public List<Dumpster> find(Integer id, Double price, Integer size, Integer company_id, Integer statusid) {

        String query = "select P from Tb_Dumpster as P ";
        String condicao = "where";

        if(id != null) {
            query += condicao + " P.id = :id";
            condicao = " and ";
        }

        if(price != null) {
            query += condicao + " P.price = :price";
            condicao = " and ";
        }

        if(size != null) {
            query += condicao + " P.size = :size";
            condicao = " and ";
        }

        if(company_id != null) {
            query += condicao + " P.companyidois = :companyidois";
            condicao = " and ";
        }

        if(statusid != null){
            query += condicao + " P.statusid = :statusid";
        }

        var q = em.createQuery(query, Dumpster.class);

        if(id != null) {
            q.setParameter("id", id);
        }

        if(price != null) {
            q.setParameter("price", price);
        }

        if(size != null) {
            q.setParameter("size", size);
        }

        if(company_id != null) {
            q.setParameter("companyidois", company_id);
        }

        if(statusid != null){
            q.setParameter("statusid", statusid);
        }

        return q.getResultList();
    }
}
