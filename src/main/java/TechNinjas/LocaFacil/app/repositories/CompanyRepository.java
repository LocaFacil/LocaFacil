package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Integer> {
    @Transactional(readOnly = true)
    Optional<Company> findByEmail(String email);
}
