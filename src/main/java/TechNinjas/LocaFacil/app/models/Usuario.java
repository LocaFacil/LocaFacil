package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.UsuarioDTO;
import TechNinjas.LocaFacil.app.models.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Entity
public class Usuario implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iduser;

    @Column(nullable = false)
    @NotNull(message = "Campo NOME é requerido")
    private String nome;

//    @CPF
//    @Column(unique = true)
//    @NotNull(message = "Campo CPF é requerido")
//    private String cpf;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Campo E-MAIL é requerido")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Campo SENHA é requerido")
    private String senha;

    //Definição de tipo de usuario
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    //Ver depois
    public Usuario() {
        addPerfil(Perfil.USER);
    }

    public Usuario(Integer iduser, String nome, String email, String senha) {
        this.iduser = iduser;
        this.nome = nome;
        //this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        //Setando o ususario como usuario normal
        addPerfil(Perfil.USER);
    }

    public Usuario(UsuarioDTO obj) {
        this.iduser = obj.getIduser();
        this.nome = obj.getNome();
        //this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCod()).collect(Collectors.toSet());
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }
}
