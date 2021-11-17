package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.RequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Request Size Dumpster")
    private Integer size;

    @ApiModelProperty(value = "Request Address")
    private String address;

    @ApiModelProperty(value = "Request Address Number")
    private Integer addressnum;

    //@ApiModelProperty(value = "Request Delivery Date")
    //private Date deliverydate;

    @ApiModelProperty(value = "Request TypeTrash Dumpster")
    private String typetrash;

    @ApiModelProperty(value = "Request Date Init")
    //@NotNull(message = "Date init field is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date dateinit;

    @ApiModelProperty(value = "Request Final Date")
    //@NotNull(message = "Date final field is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
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

    public Request(Integer id, Integer size, String address, Integer addressnum , String typetrash, Date dateinit, Date datefinal, Client client, Dumpster dumpster) {
        this.id = id;
        this.size = size;
        this.address = address;
        this.addressnum = addressnum;
        this.typetrash = typetrash;
        this.dateinit = dateinit;
        this.datefinal = datefinal;
        this.client = client;
        this.dumpster = dumpster;
    }

    @Column(name = "client_id", insertable = false, updatable = false)
    private Integer clientidois;
    
    @Column(name = "dumpster_id", insertable = false, updatable = false)
    private Integer dumpsteridois;

    public Request(RequestDTO obj) {
        this.id = obj.getId();
        this.size = obj.getSize();
        this.address = obj.getAddress();
        this.addressnum = obj.getAddressnum();
        this.typetrash = obj.getTypetrash();
        this.dateinit = obj.getDateinit();
        this.datefinal = obj.getDatefinal();
        this.clientidois = obj.getClientid();
        this.dumpsteridois = obj.getDumpsterid();
    }

}