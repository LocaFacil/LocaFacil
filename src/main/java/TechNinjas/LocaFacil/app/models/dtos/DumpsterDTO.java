package TechNinjas.LocaFacil.app.models.dtos;

import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DumpsterDTO implements Serializable {
    private static final Long SerialVersionUID = 1L;

    private Integer id;
    private Integer size;
    private Double price;
    private String typetrash;
    private Integer companyId;
    private Set<Integer> status = new HashSet<>();

    public DumpsterDTO(Dumpster obj){
        this.id = obj.getId();
        this.size = obj.getSize();
        this.price = obj.getPrice();
        this.typetrash = obj.getTypetrash();
        this.companyId = obj.getCompany().getId();
        this.status = obj.getStatus().stream().map(x -> x.getCod()).collect(Collectors.toSet());
    }

    public Set<Status> getStatus() {
        return status.stream().map(x -> Status.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(Status stat) {
        status.add(stat.getCod());
    }
}
