package TechNinjas.LocaFacil.app.models.dtos;

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
    private Date dateInit;
    private Date dateFinal;
    private Integer client;

    public RequestDTO(RequestDTO obj) {
        this.id = obj.getId();
        this.dateInit = obj.getDateInit();
        this.dateFinal = obj.getDateFinal();
        this.client = obj.getClient();
    }
}
