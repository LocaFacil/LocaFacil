package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.CompanyDTO;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "Company ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Company name")
    @Column(nullable = false)
    @NotNull(message = "Name field is required")
    private String name;

    @ApiModelProperty(value = "Company Email")
    @Column(unique = true, nullable = false)
    @NotNull(message = "Email field is required")
    private String email;

    @ApiModelProperty(value = "Company Phone")
    @Column(length = 11)
    @NotNull(message = "Phone field is required")
    private String phone;

    @ApiModelProperty(value = "Company Cnpj")
    @Column(length = 14, unique = true, nullable = false)
    @NotNull(message = "Cnpj field is required")
    private String cnpj;

    @ApiModelProperty(value = "Company Password")
    @Column(nullable = false)
    @NotNull(message = "Password field is required")
    private String password;

    //@ApiModelProperty(value = "Dumpster management")
    //@OneToOne
    //private Dumpster dumpster;

    @ApiModelProperty(value = "Company Type")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Profiles")
    private Set<Integer> profiles = new HashSet<>();

    public Company() {
        addProfile(Profile.ADMIN);
    }

    public Company(Integer id, String name,String email, String phone, String cnpj, String password) {
        this.id= id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cnpj = cnpj;
        this.password = password;
        addProfile(Profile.ADMIN);
    }

    public Company(CompanyDTO obj) {
        this.id = obj.getId();
        this.name = obj.getName();
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
