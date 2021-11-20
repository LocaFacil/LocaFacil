package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Company;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.repositories.CompanyRepository;
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
    private ClientRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> user = repository.findByEmail(email);
        Optional<Company> userCompany = companyRepository.findByEmail(email);

        if (user.isPresent()){
            return new UserSS(user.get().getId(), user.get().getName(),user.get().getEmail(), user.get().getPassword(), user.get().getProfiles());
        }else{
            if (userCompany.isPresent()){
                return new UserSS(userCompany.get().getId(), userCompany.get().getName(),userCompany.get().getEmail(), userCompany.get().getPassword(), userCompany.get().getProfiles());
            }else{
                throw new UsernameNotFoundException(email);
            }
        }
    }
}
