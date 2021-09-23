package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.CompanyDTO;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity(name = "Tb_Company")
public class Company implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Campo E-MAIL é requerido")
    private String email;

    @Column(length = 11)
    private String phone;

    @Column(length = 14, unique = true, nullable = false)
    private String cnpj;

    @Column(nullable = false)
    @NotNull(message = "Campo SENHA é requerido")
    private String password;

    @OneToOne
    private Dumpster dumpster;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Profiles")
    private Set<Integer> profiles = new HashSet<>();

    public Company() {
        addProfile(Profile.ADMIN);
    }

    public Company(Integer id, String email, String phone, String cnpj, String password) {
        this.id= id;
        this.email = email;
        this.phone = phone;
        this.cnpj = cnpj;
        this.password = password;
        addProfile(Profile.ADMIN);
    }

    public Company(CompanyDTO obj) {
        this.id = obj.getId();
        this.email = obj.getEmail();
        this.phone = obj.getPhone();
        this.cnpj = obj.getCnpj();
        this.password = obj.getPassword();
        this.profiles= obj.getProfiles().stream().map(x -> x.getCod()).collect(Collectors.toSet());
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        profiles.add(profile.getCod());
    }

    public Set<Profile> getIdProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }
}
