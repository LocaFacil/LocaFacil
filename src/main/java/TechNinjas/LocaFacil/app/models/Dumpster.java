package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.DumpsterDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
//@AllArgsConstructor
@Entity(name = "Tb_Dumpster")
public class Dumpster implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @ApiModelProperty(value = "Dumpster ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Dumpster Quantity")
    private Integer quantity;

    @ApiModelProperty(value = "Dumpster Size")
    private Integer size;

    @ApiModelProperty(value = "Dumpster Price")
    @Column(name = "price", nullable = false)
    private double price;

    @ApiModelProperty(value = "Dumpster Type")
    private String typetrash;

    @ApiModelProperty(value = "Dumpster-Company")
    //@OneToOne()
    private Integer company;

    private Integer idcompany;

    public Dumpster(Integer id, Integer quantity, Integer size, double price, String typetrash){
        this.id = id;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
        this.typetrash = typetrash;
        this.company = company;
    }

    public Dumpster(DumpsterDTO obj){
        this.id = obj.getId();
        this.quantity = obj.getQuantity();
        this.size = obj.getSize();
        this.price = obj.getPrice();
        this.typetrash = obj.getTypetrash();
        this.company = obj.getCompany();
    }

    public Integer getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(Integer idcompany) {
        this.idcompany = idcompany;
    }
}
