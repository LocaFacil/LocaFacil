package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.RequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity(name = "Tb_Request")
public class Request implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @ApiModelProperty(value = "Request ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Request Date Init")
    @NotNull(message = "Date init field is required")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date dateinit;

    @ApiModelProperty(value = "Request Final Date")
    @NotNull(message = "Date final field is required")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date datefinal;

    @ApiModelProperty(value = "Request-Client")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ApiModelProperty(value = "Request-Dumpster")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dumpster_id", referencedColumnName = "id")
    private Dumpster dumpster;

    public Request() {
    }

    public Request(Integer id, Date dateinit, Date datefinal, Client client, Dumpster dumpster) {
        this.id = id;
        this.dateinit = dateinit;
        this.datefinal = datefinal;
        this.client = client;
        this.dumpster = dumpster;
    }

    private transient Integer clientidois;
    private transient Integer dumpsteridois;

    public Request(RequestDTO obj) {
        this.id = obj.getId();
        this.dateinit = obj.getDateinit();
        this.datefinal = obj.getDatefinal();
        this.clientidois = obj.getClientid();
        this.dumpsteridois = obj.getDumpsterid();
    }


}