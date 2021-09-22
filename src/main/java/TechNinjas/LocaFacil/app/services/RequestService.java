package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Request;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import TechNinjas.LocaFacil.app.repositories.RequestRepository;
import TechNinjas.LocaFacil.app.security.UserSS;
import TechNinjas.LocaFacil.app.services.exceptions.AuthorizationException;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    RequestRepository repository;

    public Request findById(Integer id) {
        UserSS userSS = UserService.authenticated();
        if((userSS == null || !userSS.hasRole(Profile.ADMIN)) && !id.equals(userSS.getId())) {
            throw new AuthorizationException("Acesso negado!");
        }

        Optional<Request> obj = repository.findById(id);
        return obj.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName())
        );
    }

    public Request create(Request request) {
        request.setIdrequest(null);
        //dump.setCompany(dump.getCompany());
        return repository.save(request);
    }

    public List<Request> findAll() {
        return repository.findAll();
    }

}
