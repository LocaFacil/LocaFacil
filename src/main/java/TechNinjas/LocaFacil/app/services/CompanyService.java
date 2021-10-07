package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Company;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import TechNinjas.LocaFacil.app.repositories.CompanyRepository;
import TechNinjas.LocaFacil.app.services.exceptions.AuthorizationException;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private ModelMapper mapper = new ModelMapper();

    public Company findById(Integer id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Company> company = repository.findByEmail(email);
        if(company.isPresent()) {
            if ((!company.get().getProfiles().contains(Profile.ADMIN)) && !id.equals(company.get().getId())) {
                throw new AuthorizationException("Acesso negado!");
            } else {
                Optional<Company> obj = repository.findById(id);
                return obj.orElseThrow(() ->
                        new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName()));
            }
        }
        return null;
    }

    public List<Company> findAll() {
        return repository.findAll();
    }

    public Company create(Company obj) {
        obj.setId(null);
        obj.setPassword(encoder.encode(obj.getPassword()));
        return repository.save(obj);
    }

    public Company update(Integer id, @Valid Company obj) {
        obj.setId(id);
        Company company = findById(id);
        company = mapper.map(obj, Company.class);
        company.setPassword(encoder.encode(obj.getPassword()));
        return repository.save(company);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
