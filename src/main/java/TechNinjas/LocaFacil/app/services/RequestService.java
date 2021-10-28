package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.Request;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import TechNinjas.LocaFacil.app.models.enums.Status;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.repositories.DumpsterRepository;
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

    @Autowired
    DumpsterRepository dumpsterRepository;

    private ModelMapper mapper = new ModelMapper();

    public Request findById(Integer id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = clientRepository.findByEmail(email);
        if(client.isPresent()) {
            //Fazer uma verificação para ver ser essa requisição é do cliente X
            if ((!client.get().getProfiles().contains(Profile.ADMIN)) /*&& !request.getClient().equals(client.get().getId())*/) {
                throw new AuthorizationException("Acesso negado!");
            } else {
                Optional<Request> obj = repository.findById(id);
                return obj.orElseThrow(() ->
                        new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName()));
            }
        }
        return null;
    }

    //Primeiro tem que ser verificado antes da solicitação, se o cliente tem cpf cadastrado, e endereço ele avança para
    //proxima tela.

    public Request create(Request request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = clientRepository.findByEmail(email);
        //Ver parte de update no cliente já existe porem terminar de colocar os dados que falta
//        if(client.get().getAddress() == null){
//            if(client.get().getAddressnum() == null){
//                if(client.get().getPhone() == null){
//                    if(client.get().getCpf() == null){
//                        //client.get().setCpf();
//                    }
//                }
//            }
//        }
        request.setId(null);
        request.setClient(client.get());
        //Pensar numa forma de verificação, só deixar criar requisição caso tenha caçamba disponivel
        List<Dumpster> dumpster = dumpsterRepository.findAll();
        if(dumpster.contains(Status.AVAILABLE)){
            //request.setDumpsteridois();
            return repository.save(request);
            //Parte de pagamento
        }
        return null;
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
