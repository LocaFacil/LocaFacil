package TechNinjas.LocaFacil.app.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity(name = "Tb_Request")
public class Request implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idrequest;

    private Date senddate;

    @OneToMany
    private List<Client> client;

    @OneToMany
    private List<Company> company;
}