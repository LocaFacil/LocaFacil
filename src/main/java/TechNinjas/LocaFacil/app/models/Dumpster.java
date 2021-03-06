package TechNinjas.LocaFacil.app.models;

import TechNinjas.LocaFacil.app.models.dtos.DumpsterDTO;
import TechNinjas.LocaFacil.app.models.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity(name = "Tb_Dumpster")
public class Dumpster implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @ApiModelProperty(value = "Dumpster ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Dumpster Size")
    private Integer size;

    @ApiModelProperty(value = "Dumpster Price")
    @Column(name = "price", nullable = false)
    private double price;

    @ApiModelProperty(value = "Dumpster-Company")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ApiModelProperty(value = "Dumpster Status")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Status")
    private Set<Integer> status = new HashSet<>();

    @ApiModelProperty(value = "Dumpster Status Id")
    @Column(name = "statusid")
    private Integer statusid;
    //1-Available, 2-Busy, 3-Waiting, 4-Release

    public Dumpster(){
        addStatus(Status.AVAILABLE);
    }

    public Dumpster(Integer id, Integer size, double price, Company company, Integer statusid){
        this.id = id;
        this.size = size;
        this.price = price;
        this.company = company;
        this.statusid = statusid;
        addStatus(Status.AVAILABLE);
    }

    @Column(name = "company_id", insertable = false, updatable = false)
    private Integer companyidois;

    public Dumpster(DumpsterDTO obj){
        this.id = obj.getId();
        this.size = obj.getSize();
        this.price = obj.getPrice();
        this.companyidois = obj.getCompanyId();
        this.status = obj.getStatus().stream().map(x -> x.getCod()).collect(Collectors.toSet());
        this.statusid = obj.getStatusid();
    }

    public Set<Status> getStatus() {
        return status.stream().map(x -> Status.toEnum(x)).collect(Collectors.toSet());
    }

    public void addStatus(Status statuss) {
        status.add(statuss.getCod());
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
