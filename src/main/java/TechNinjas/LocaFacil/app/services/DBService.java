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
//        Client u1 = new Client(null, "Admin Teste", "admin@mail.com", encoder.encode("1313"));
//        u1.addProfile(Profile.ADMIN);
//
//        Client u2 = new Client(null, "Vinicius Belmont","darkvaderlol2017@gmail.com", encoder.encode("vini123"));
////
//        companyRepository.saveAll(Arrays.asList(u1));
    }
}
