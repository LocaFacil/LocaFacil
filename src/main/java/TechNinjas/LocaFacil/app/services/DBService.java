package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instanciaDB() {
//        Usuario u1 = new Usuario(null, "Admin Teste", "admin@mail.com", encoder.encode("1313"));
//        u1.addPerfil(Perfil.ADMIN);
//
//        Usuario u2 = new Usuario(null, "Vinicius Belmont", "darkvader@gmail.com", encoder.encode("vini123"));
//
//        usuarioRepository.saveAll(Arrays.asList(u1, u2));
    }
}
