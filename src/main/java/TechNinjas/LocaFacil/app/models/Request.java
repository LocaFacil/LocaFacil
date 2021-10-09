package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.RequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Date dateInit;

    @ApiModelProperty(value = "Request Final Date")
    @NotNull(message = "Date final field is required")
    private Date dateFinal;

    @ApiModelProperty(value = "Request-Client")
    private Integer client;

    public Request(Integer id, Date dateInit, Date dateFinal, Integer client) {
        this.id = id;
        this.dateInit = dateInit;
        this.dateFinal = dateFinal;
        this.client = client;
    }

    public Request(RequestDTO obj) {
        this.id = obj.getId();
        this.dateInit = obj.getDateInit();
        this.dateFinal = obj.getDateFinal();
        this.client = obj.getClient();
    }


}