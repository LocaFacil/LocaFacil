package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.CompanyDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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

    @Column(nullable = false)
    @NotNull(message = "Campo SENHA é requerido")
    private String password;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "Profiles")
//    private Set<Integer> profiles = new HashSet<>();

    public Company(Integer id, String email, String phone, String password) {
        this.id= id;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Company(CompanyDTO obj) {
        this.id = obj.getId();
        this.email = obj.getEmail();
        this.phone = obj.getPhone();
        this.password = obj.getPassword();
    }
}
