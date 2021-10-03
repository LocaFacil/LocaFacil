package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Company;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.repositories.CompanyRepository;
import TechNinjas.LocaFacil.app.services.exceptions.CustomerNotFoundException;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        //UserSS userSS = UserService.authenticated();
        //Ele ta barrando no userSS, como ele fosse nulo
//        if((userSS == null || !userSS.hasRole(Profile.ADMIN)) && !id.equals(userSS.getId())) {
//            throw new AuthorizationException("Acesso negado!");
//        }

        Optional<Client> obj = repository.findById(id);
        return obj.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName())
        );
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

//    public Client findByIdTeste(Integer id) {
//        UserSS userSS = UserService.authenticated();
//        //CADE O BAHIANO
//        if(userSS == null){
//            System.out.println("userss É NULOOO");
//        }
//        if(id == null){
//            System.out.println("id colocado no postman É NULOOO");
//        }
//
//        Optional<Client> obj = repository.findById(id);
//        return obj.orElseThrow(() ->
//                new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName())
//        );
//    }
}





















