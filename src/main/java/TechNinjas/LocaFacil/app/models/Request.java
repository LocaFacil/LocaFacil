package TechNinjas.LocaFacil.app.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity(name = "Tb_Request")
public class Request implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @ApiModelProperty(value = "Request ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idrequest;

    @ApiModelProperty(value = "Request Date Init")
    private Date dataInicio;

    @ApiModelProperty(value = "Request Final Date")
    private Date dataFim;

    @ApiModelProperty(value = "Request-Client")
    @OneToOne
    private Client client;

    //@ApiModelProperty(value = "Request-Dumpster")
    //@OneToOne
    //private Dumpster dumpster;
}