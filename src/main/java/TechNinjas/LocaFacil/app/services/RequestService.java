package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Request;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.repositories.RequestRepository;
import TechNinjas.LocaFacil.app.services.exceptions.AuthorizationException;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    RequestRepository repository;

    @Autowired
    ClientRepository clientRepository;

    private ModelMapper mapper = new ModelMapper();

    public Request findById(Integer id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = clientRepository.findByEmail(email);
        if(client.isPresent()) {
            if ((!client.get().getProfiles().contains(Profile.ADMIN)) && !id.equals(client.get().getId())) {
                throw new AuthorizationException("Acesso negado!");
            } else {
                Optional<Request> obj = repository.findById(id);
                return obj.orElseThrow(() ->
                        new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName()));
            }
        }
        return null;
    }

    public Request create(Request request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = clientRepository.findByEmail(email);
        request.setId(null);
        //Colocar verificador para ver se a caçamba está livre ou ocupada
        //Colocar um update que ira tirar da quantidade de caçambas disponiveis
        request.setClient(client.get().getId());
        //dump.setCompany(dump.getCompany());
        return repository.save(request);
    }

    public List<Request> findAll() {
        return repository.findAll();
    }

    public Request update(Integer id, @Valid Request obj) {
        obj.setId(id);
        Request request = findById(id);
        request = mapper.map(obj, Request.class);
        //Muda o status da caçamba para AVAILABLE, assim  como tambem fala para ser RECOLHIDO
        return repository.save(request);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

}
