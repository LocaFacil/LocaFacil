package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private ClientRepository companyRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instanciaDB() {
    }
}
