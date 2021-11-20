package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Company;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.repositories.CompanyRepository;
import TechNinjas.LocaFacil.app.services.exceptions.AuthorizationException;
import TechNinjas.LocaFacil.app.services.exceptions.CustomerNotFoundException;
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
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private ModelMapper mapper = new ModelMapper();

    public Client findById(Integer id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = repository.findByEmail(email);
        if(client.isPresent()) {
            if ((!client.get().getProfiles().contains(Profile.ADMIN)) && !id.equals(client.get().getId())) {
                throw new AuthorizationException("Acesso negado!");
            } else {
                Optional<Client> obj = repository.findById(id);
                return obj.orElseThrow(() ->
                        new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName()));
            }
        }
        return null;
    }

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client create(Client obj) {
        obj.setId(null);
        obj.setPassword(encoder.encode(obj.getPassword()));
        return repository.save(obj);
    }

	public Client update(Integer id, @Valid Client obj) {
        obj.setId(id);
        Client client = findById(id);
        client = mapper.map(obj, Client.class);
        client.setPassword(encoder.encode(obj.getPassword()));
        return repository.save(client);
	}

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    public void updateResetPasswordToken(String password, String email) throws CustomerNotFoundException {
        Optional<Client> obj = repository.findByEmail(email);
        Optional<Company> obj2 = companyRepository.findByEmail(email);
        if (obj.isPresent()) {
            obj.get().setPassword(password);
            repository.save(obj.get());
        } else {
            if (obj2.isPresent()){
                obj2.get().setPassword(password);
                companyRepository.save(obj2.get());
            }else{
                throw new CustomerNotFoundException("Could not find a user with this email: " + email);
            }
        }
    }

    public Boolean checkUser(Integer id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = repository.findByEmail(email);
        if(client.isPresent()) {
            if(!id.equals(client.get().getId())) {
                throw new AuthorizationException("Acesso negado!");
            }else{
                try{
                    if(!client.get().getCpf().isEmpty()){
                        if(!client.get().getPhone().isEmpty()){
                            if(!client.get().getAddress().isEmpty()){
                                if(!client.get().getAddressnum().equals(null)){
                                    return true;
                                }else{
                                    return false;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                }
            }
        }
        return false;
    }

    public Client updateCheck(Integer id, @Valid Client obj) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> clientrecap = repository.findByEmail(email);
        obj.setId(id);
        obj.setName(clientrecap.get().getName());
        obj.setEmail(clientrecap.get().getEmail());
        obj.setPassword(clientrecap.get().getPassword());
        Client client = findById(id);
        client = mapper.map(obj, Client.class);
        client.setName(obj.getName());
        client.setEmail(obj.getEmail());
        client.setPassword(obj.getPassword());
        return repository.save(client);
    }

    public Boolean checktermsUser(Integer id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> client = repository.findByEmail(email);
        if(client.isPresent()) {
            if(!id.equals(client.get().getId())) {
                throw new AuthorizationException("Acesso negado!");
            }else{
                try{
                    if(!client.get().getTermsUse().equals(null)){
                       return true;
                    }else {
                       return false;
                    }
                }catch (Exception e){
                }
            }
        }
        return false;
    }

    public Client updatetermsCheck(Integer id, @Valid Client obj) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Client> clientrecap = repository.findByEmail(email);
        obj.setId(id);
        obj.setName(clientrecap.get().getName());
        obj.setEmail(clientrecap.get().getEmail());
        obj.setPassword(clientrecap.get().getPassword());
        obj.setCpf(clientrecap.get().getCpf());
        obj.setPhone(clientrecap.get().getPhone());
        obj.setAddress(clientrecap.get().getAddress());
        obj.setAddressnum(clientrecap.get().getAddressnum());
        Client client = findById(id);
        client = mapper.map(obj, Client.class);
        client.setName(obj.getName());
        client.setEmail(obj.getEmail());
        client.setPassword(obj.getPassword());
        client.setCpf(obj.getCpf());
        client.setPhone(obj.getPhone());
        client.setAddress(obj.getAddress());
        client.setAddressnum(obj.getAddressnum());
        return repository.save(client);
    }
}





















