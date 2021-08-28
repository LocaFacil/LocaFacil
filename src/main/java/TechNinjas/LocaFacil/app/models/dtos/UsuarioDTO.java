package TechNinjas.LocaFacil.app.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import TechNinjas.LocaFacil.app.models.Usuario;
import TechNinjas.LocaFacil.app.models.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {
    private static final Long SerialVersionUID = 1L;

    private Integer iduser;
    private String nome;
    //private String cpf;
    private String email;

    @JsonIgnore
    private String senha;
    private Set<Integer> perfis = new HashSet<>();

    public UsuarioDTO(Usuario obj) {
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
}































