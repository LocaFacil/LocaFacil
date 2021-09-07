package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.repositories.UsuarioRepository;
import TechNinjas.LocaFacil.app.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Interface central que carrega dados específicos do usuário
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

//    @Autowired
//    private CompanyRepository companyRepository;

    // Localiza o usuário com base no nome de usuário
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Vou ter que fazer separação empresa e cliente
        Optional<Client> user = repository.findByEmail(email);
        //Optional<Company> userCompany = companyRepository.findByEmail(email);
        if(!user.isPresent()/*&&!userCompany.isPresent()*/) {
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getPassword(), user.get().getProfiles());
    }
}
