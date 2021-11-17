package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.Request;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.repositories.CompanyRepository;
import TechNinjas.LocaFacil.app.repositories.DumpsterRepository;
import TechNinjas.LocaFacil.app.repositories.RequestRepository;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RequestService {

    @Autowired
    RequestRepository repository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DumpsterRepository dumpsterRepository;

    @Autowired
    CompanyRepository companyRepository;

    private ModelMapper mapper = new ModelMapper();

    public Request findById(Integer id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = clientRepository.findByEmail(email);
        List<Request> req = repository.findAllClientById(id);
        if(client.isPresent()) {
             Optional<Request> obj = repository.findById(id);
             return obj.orElseThrow(() ->
                 new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName()));
        }
        return null;
    }

    public Request create(Request request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = clientRepository.findByEmail(email);
        request.setId(null);
        request.setClient(client.get());
        request.setDatefinal(java.sql.Date.valueOf(request.getDateinit().toLocalDate().plusDays(9)));
        if(request.getSize() == 1){
            List<Dumpster> dumpster = dumpsterRepository.getDumpsterByStatusIdAndSizeSmall();
            try{
                Collections.shuffle(dumpster);
                int randomLeght = 1;
                List<Dumpster> dump3 = dumpster.subList(0, randomLeght);
                request.setDumpster(dump3.get(0));
                request.getDumpster().setStatusid(3);
                request.getDumpster().setStatus(Set.of(3));
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
                request.getDumpster().setStatusid(3);
                request.getDumpster().setStatus(Set.of(3));
                return repository.save(request);
            }catch (Exception e){
                System.out.println(e);
                return null;
            }
        }
    }

    public List<Request> findAllClientById(Integer id) {
        return repository.findAllClientById(id);
    }

    public List<Request> findAll() {
        return repository.findAll();
    }

    public Request update(Integer id, @Valid Request obj) {
        Optional<Request> req = repository.findById(id);
        Date date = new Date(System.currentTimeMillis());
        java.sql.Date datePresent = new java.sql.Date(date.getTime());

        try{
            obj.setId(id);
            obj.setSize(req.get().getSize());
            obj.setAddress(req.get().getAddress());
            obj.setAddressnum(req.get().getAddressnum());
            obj.setTypetrash(req.get().getTypetrash());
            obj.setDateinit(req.get().getDateinit());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(String.valueOf(req.get().getDatefinal()));
            Date date2 = sdf.parse(String.valueOf(datePresent));
            int result = date2.compareTo(date1);
            if(result==0){
                obj.setDatefinal(java.sql.Date.valueOf(req.get().getDatefinal().toLocalDate().plusDays(9)));
                obj.setClient(req.get().getClient());
                obj.setDumpster(req.get().getDumpster());
                obj.getDumpster().setStatusid(4);
                obj.getDumpster().setStatus(Set.of(4));
                Request request = findById(id);
                request = mapper.map(obj, Request.class);
                return repository.save(request);
            }else{
                return null;
            }
        }catch (Exception e){
            System.out.println("Erro: " + e);
        }
        return null;
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    public Request liberateUpdate(Integer id, @Valid Request obj){
        Optional<Request> req = repository.findById(id);
        try{
            obj.setId(id);
            obj.setSize(req.get().getSize());
            obj.setAddress(req.get().getAddress());
            obj.setAddressnum(req.get().getAddressnum());
            obj.setTypetrash(req.get().getTypetrash());
            obj.setDateinit(req.get().getDateinit());
            obj.setDatefinal(req.get().getDatefinal());
            obj.setClient(req.get().getClient());
            obj.setDumpster(req.get().getDumpster());
            obj.getDumpster().setStatusid(4);
            obj.getDumpster().setStatus(Set.of(4));
            Request request = findById(id);
            request = mapper.map(obj, Request.class);
            return repository.save(request);
        }catch (Exception e){
            System.out.println("Erro: " + e);
        }
        return null;
    }

    public Request deliverUpdate(Integer id, @Valid Request obj){
        Optional<Request> req = repository.findById(id);
        try{
            obj.setId(id);
            obj.setSize(req.get().getSize());
            obj.setAddress(req.get().getAddress());
            obj.setAddressnum(req.get().getAddressnum());
            obj.setTypetrash(req.get().getTypetrash());
            obj.setDateinit(req.get().getDateinit());
            obj.setDatefinal(req.get().getDatefinal());
            obj.setClient(req.get().getClient());
            obj.setDumpster(req.get().getDumpster());
            obj.getDumpster().setStatusid(2);
            obj.getDumpster().setStatus(Set.of(2));
            Request request = findById(id);
            request = mapper.map(obj, Request.class);
            return repository.save(request);
        }catch (Exception e){
            System.out.println("Erro: " + e);
        }
        return null;
    }

    public Request retreatUpdate(Integer id, @Valid Request obj){
        Optional<Request> req = repository.findById(id);
        try{
            obj.setId(id);
            obj.setSize(req.get().getSize());
            obj.setAddress(req.get().getAddress());
            obj.setAddressnum(req.get().getAddressnum());
            obj.setTypetrash(req.get().getTypetrash());
            obj.setDateinit(req.get().getDateinit());
            obj.setDatefinal(req.get().getDatefinal());
            obj.setClient(req.get().getClient());
            obj.setDumpster(req.get().getDumpster());
            obj.getDumpster().setStatusid(1);
            obj.getDumpster().setStatus(Set.of(1));
            Request request = findById(id);
            request = mapper.map(obj, Request.class);
            return repository.save(request);
        }catch (Exception e){
            System.out.println("Erro: " + e);
        }
        return null;
    }

    public List<Request> findAllDeliversAndRetreat(){
        return repository.findAllDeliversAndRetreat();
    }
}
