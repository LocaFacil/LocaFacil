package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.ClientDTO;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Entity(name = "Tb_Client")
public class Client implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @ApiModelProperty(value = "User ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "User Name")
    @Column(nullable = false, length = 50)
    @NotNull(message = "Name field is required")
    private String name;

    @ApiModelProperty(value = "User Email")
    @Column(unique = true, nullable = false)
    @NotNull(message = "Email field is required")
    private String email;

    @ApiModelProperty(value = "User CPF")
    @Column(unique = true)
    private String cpf;

    @ApiModelProperty(value = "User Phone")
    @Column(length = 11)
    private String phone;

    @ApiModelProperty(value = "User Password")
    @Column(nullable = false)
    @NotNull(message = "Password field is required")
    private String password;

    @ApiModelProperty(value = "User address")
    private String address;

    @ApiModelProperty(value = "User address number")
    private Integer addressnum;

    @ApiModelProperty(value = "Request management")
    @OneToMany(mappedBy = "client")
    private List<Request> request = new ArrayList<>();

//    @Column()
//    private Boolean termsUse;

    //Definição de tipo de usuario
    @ApiModelProperty(value = "User Type")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Profiles")
    private Set<Integer> profiles = new HashSet<>();

    //@ApiModelProperty(value = "User Reset Password")
    //@Column(name = "reset_password_token")
    //private String resetPasswordToken;

    //@ApiModelProperty(value = "User Requests")
    //@OneToOne
    //private Request request;

    //Ver depois
    public Client() {
        addProfile(Profile.USER);
    }

    public Client(Integer id, String name, String email, String password/*,String cpf, String phone*/) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        //Setando o ususario como usuario normal
        addProfile(Profile.USER);
    }

    public Client(ClientDTO obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.email = obj.getEmail();
        this.password = obj.getPassword();
        this.cpf = obj.getCpf();
        this.phone = obj.getPhone();
        this.address = obj.getAddress();
        this.addressnum = obj.getAddressnum();
        this.profiles= obj.getProfiles().stream().map(x -> x.getCod()).collect(Collectors.toSet());
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        profiles.add(profile.getCod());
    }

    //public void setResetPasswordToken(String resetPasswordToken) {
        //this.resetPasswordToken = resetPasswordToken;
    //}

    //public String getResetPasswordToken() {
        //return resetPasswordToken;
    //}

    public Set<Profile> getIdProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }
}
