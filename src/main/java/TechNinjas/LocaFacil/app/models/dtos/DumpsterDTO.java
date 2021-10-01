package TechNinjas.LocaFacil.app.models.dtos;

import TechNinjas.LocaFacil.app.models.Dumpster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DumpsterDTO implements Serializable {
    private static final Long SerialVersionUID = 1L;

    private Integer id;
    private Integer quantity;
    private Integer size;
    private Double price;
    private String typetrash;
    private Integer company;

    public DumpsterDTO(Dumpster obj){
        this.id = obj.getId();
        this.quantity = obj.getQuantity();
        this.size = obj.getSize();
        this.price = obj.getPrice();
        this.typetrash = obj.getTypetrash();
        this.company = obj.getCompany();
    }
}
