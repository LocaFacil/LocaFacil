package TechNinjas.LocaFacil.app.models.dtos;

import TechNinjas.LocaFacil.app.models.Company;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CompanyDTO implements Serializable {
    private static final Long SerialVersionUID = 1L;

    private Integer id;
    private String email;
    private String phone;

    @JsonIgnore
    private String password;
    private Set<Integer> profiles = new HashSet<>();

    public CompanyDTO(Company obj) {
        this.id = obj.getId();
        this.email = obj.getEmail();
        this.phone = obj.getPhone();
        this.password = obj.getPassword();
        this.profiles = obj.getProfiles().stream().map(x -> x.getCod()).collect(Collectors.toSet());
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }
//
    public void addProfile(Profile profile) {
        profiles.add(profile.getCod());
    }
}
