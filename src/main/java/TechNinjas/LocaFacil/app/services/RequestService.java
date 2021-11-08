package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.Request;
import TechNinjas.LocaFacil.app.models.enums.Profile;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Request create(Request request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = clientRepository.findByEmail(email);
        request.setId(null);
        request.setClient(client.get());
        //Primeiro fazer um repository o qual puxa todas as caçambas livres
        //List<Dumpster> dumpster = dumpsterRepository.getDumpsterByStatusId();
        //Fazer verificação de tamanho
        if(request.getSize() == 1){
            List<Dumpster> dumpster = dumpsterRepository.getDumpsterByStatusIdAndSizeSmall();
            try{
                Collections.shuffle(dumpster);
                int randomLeght = 1;
                List<Dumpster> dump3 = dumpster.subList(0, randomLeght);
                request.setDumpster(dump3.get(0));
                request.getDumpster().setStatusid(2);
                request.getDumpster().setStatus(Set.of(2));
                return repository.save(request);
            }catch (Exception e){
                System.out.println(e);
                return null;
            }
        }else{
            List<Dumpster> dumpster = dumpsterRepository.getDumpsterByStatusIdAndSizeBig();
            try{
                Collections.shuffle(dumpster);
                int randomLeght = 1;
                List<Dumpster> dump3 = dumpster.subList(0, randomLeght);
                request.setDumpster(dump3.get(0));
                request.getDumpster().setStatusid(2);
                request.getDumpster().setStatus(Set.of(2));
                return repository.save(request);
            }catch (Exception e){
                System.out.println(e);
                return null;
            }
        }
//        try{
//            //Fazer jeito para que verifique se a caçamba está livre ou não
//            //if(dumpster){
//                Collections.shuffle(dumpster);
//                int randomLeght = 1;
//                List<Dumpster> dump3 = dumpster.subList(0, randomLeght);
//                request.setDumpster(dump3.get(0));
//                request.getDumpster().setStatusid(2);
//                request.getDumpster().setStatus(Set.of(2));
//                return repository.save(request);
////            }else{
////                System.out.println("\n Nao foi possivel solicitar");
////                return null;
////            }
//        }catch (Exception e){
//            System.out.println(e);
//            return null;
//        }
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

    //Liberar
    public Request liberateUpdate(Integer id, @Valid Request obj){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = clientRepository.findByEmail(email);
        Optional<Request> req = repository.findById(id);
        obj.setId(id);
        obj.setSize(req.get().getSize());
        obj.setAddress(req.get().getAddress());
        obj.setAddressnum(req.get().getAddressnum());
        obj.setTypetrash(req.get().getTypetrash());
        obj.setDateinit(req.get().getDateinit());
        obj.setDatefinal(req.get().getDatefinal());
        obj.setClient(client.get());
        obj.setDumpster(req.get().getDumpster());
        obj.getDumpster().setStatusid(1);
        obj.getDumpster().setStatus(Set.of(1));
        Request request = findById(id);
        request = mapper.map(obj, Request.class);
//        request.setSize(obj.getSize());
//        request.setAddress(obj.getAddress());
//        request.setAddressnum(obj.getAddressnum());
//        request.setTypetrash(obj.getTypetrash());
//        request.setDateinit(obj.getDateinit());
//        request.setDatefinal(obj.getDatefinal());
//        request.setClient(obj.getClient());
//        request.setDumpster(obj.getDumpster());
        return repository.save(request);
    }

}
