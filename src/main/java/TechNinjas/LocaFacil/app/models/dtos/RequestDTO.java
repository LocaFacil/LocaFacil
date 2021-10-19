package TechNinjas.LocaFacil.app.models.dtos;

import TechNinjas.LocaFacil.app.models.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO implements Serializable {
    private static final Long SerialVersionUID = 1L;

    private Integer id;
    private Date dateinit;
    private Date datefinal;
    private Integer client;
    private Integer dumpster;

    public RequestDTO(Request obj) {
        this.id = obj.getId();
        this.dateinit = obj.getDateinit();
        this.datefinal = obj.getDatefinal();
        this.client = obj.getClient();
        this.dumpster = obj.getDumpster();
    }
}
