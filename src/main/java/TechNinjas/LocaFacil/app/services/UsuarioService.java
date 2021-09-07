package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import TechNinjas.LocaFacil.app.repositories.UsuarioRepository;
import TechNinjas.LocaFacil.app.security.UserSS;
import TechNinjas.LocaFacil.app.services.exceptions.AuthorizationException;
import TechNinjas.LocaFacil.app.services.exceptions.CustomerNotFoundException;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private ModelMapper mapper = new ModelMapper();

    public Client findById(Integer id) {
        UserSS userSS = UserService.authenticated();
        if((userSS == null || !userSS.hasRole(Profile.ADMIN)) && !id.equals(userSS.getId())) {
            throw new AuthorizationException("Acesso negado!");
        }

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
        if (obj.isPresent()) {
            obj.get().setPassword(password);
            repository.save(new Client(obj.get().getId(), obj.get().getName(), obj.get().getEmail(),
                    /*obj.get().getCpf(), obj.get().getPhone(),*/ obj.get().getPassword(),
                    obj.get().getProfiles().stream().map(Profile::getCod).collect(Collectors.toSet()),
                    obj.get().getResetPasswordToken()));
        } else {
            throw new CustomerNotFoundException("Não foi possivel encontrar um usuario com esse email: " + email);
        }
    }

//    public UsuarioRepository getByResetPasswordToken(String token) {
//        return repository.findByResetPasswordToken(token);
//    }
//
//    public void updatePassword(Client client, String newPassword) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        client.setPassword(encodedPassword);
//
//        client.setResetPasswordToken(null);
//        repository.save(client);
//    }
}





















