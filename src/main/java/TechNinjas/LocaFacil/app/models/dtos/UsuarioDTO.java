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

    private Integer id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private Boolean termsUse;

    @JsonIgnore
    private String password;
    private Set<Integer> perfis = new HashSet<>();

    public UsuarioDTO(Usuario obj) {
        this.id = obj.getId();
        this.name = obj.getName();
//        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
//        this.phone = obj.getPhone();
        this.password = obj.getPassword();
//        this.termsUse = obj.getTermsUse();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCod()).collect(Collectors.toSet());
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }
}































