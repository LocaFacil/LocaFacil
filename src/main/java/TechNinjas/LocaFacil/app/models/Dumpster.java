package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.DumpsterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity(name = "Tb_Dumpster")
public class Dumpster implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    private Integer size;

    @Column(name = "price", nullable = false)
    private double price;

    private String typetrash;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = true)
//    @JsonIgnore
    @OneToOne
    private Company company;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "idrequest", referencedColumnName = "idrequest", insertable = false, updatable = false)
//    @JsonIgnore
//    private Request request;

    public Dumpster(Integer id, Integer quantity, Integer size, double price, Company company, String typetrash){
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
}
