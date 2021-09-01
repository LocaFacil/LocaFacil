package TechNinjas.LocaFacil.app.repositories;

import TechNinjas.LocaFacil.app.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Transactional(readOnly = true)
    Optional<Usuario> findByEmail(String email);

    //public Usuario findByUserEmail(String email);
    UsuarioRepository findByResetPasswordToken(String token);
}
