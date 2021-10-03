package TechNinjas.LocaFacil.app.models.dtos;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.enums.Profile;
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
public class ClientDTO implements Serializable {
    private static final Long SerialVersionUID = 1L;

    private Integer id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private Boolean termsUse;

    //@JsonIgnore
    private String password;
    private Set<Integer> profiles = new HashSet<>();

    public ClientDTO(Client obj) {
        this.id = obj.getId();
        this.name = obj.getName();
//        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
//        this.phone = obj.getPhone();
        this.password = obj.getPassword();
//        this.termsUse = obj.getTermsUse();
        this.profiles = obj.getProfiles().stream().map(x -> x.getCod()).collect(Collectors.toSet());
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        profiles.add(profile.getCod());
    }
}































