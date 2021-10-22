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
    private Integer clientid;
    private Integer dumpsterid;

    public RequestDTO(Request obj) {
        this.id = obj.getId();
        this.dateinit = obj.getDateinit();
        this.datefinal = obj.getDatefinal();
        this.clientid = obj.getClient().getId();
        this.dumpsterid = obj.getDumpster().getId();
    }
}
