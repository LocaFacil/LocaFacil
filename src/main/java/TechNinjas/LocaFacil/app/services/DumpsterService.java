package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Company;
import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import TechNinjas.LocaFacil.app.repositories.CompanyRepository;
import TechNinjas.LocaFacil.app.repositories.DumpsterRepository;
import TechNinjas.LocaFacil.app.services.exceptions.AuthorizationException;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
public class DumpsterService {

    @Autowired
    private DumpsterRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

    private ModelMapper mapper = new ModelMapper();

    public Dumpster findById(Integer id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Company> company = companyRepository.findByEmail(email);
        if(company.isPresent()) {
            if ((!company.get().getProfiles().contains(Profile.ADMIN)) && !id.equals(company.get().getId())) {
                throw new AuthorizationException("Acesso negado!");
            } else {
                Optional<Dumpster> obj = repository.findById(id);
                return obj.orElseThrow(() ->
                        new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName()));
            }
        }
        return null;
    }

    public List<Dumpster> findAll() {
        return repository.findAll();
    }

    public Dumpster create(Dumpster dump) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Company> company = companyRepository.findByEmail(email);
        dump.setId(null);
        dump.setCompany(company.get());
        return repository.save(dump);
    }

    public Dumpster update(Integer id, @Valid Dumpster obj) {
        obj.setId(id);
        Dumpster dump = findById(id);
        dump = mapper.map(obj, Dumpster.class);
        return repository.save(dump);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    public List<Dumpster> getDumpsterByStatus(Integer statusid){
        //Set<String> status = new HashSet<String>(Arrays.asList(stat));
        return repository.getDumpsterByStatus(statusid);
    }
}





















