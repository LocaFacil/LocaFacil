package TechNinjas.LocaFacil.app.models.dtos;

import java.io.Serializable;
import java.sql.Date;

public class RequestDTO implements Serializable {
    private static final Long SerialVersionUID = 1L;

    private Integer idrequest;
    private Date dataInicio;
    private Date dataFim;

    public Integer getIdrequest() {
        return idrequest;
    }

    public void setIdrequest(Integer idrequest) {
        this.idrequest = idrequest;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}
