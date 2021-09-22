package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Company;
import TechNinjas.LocaFacil.app.models.dtos.CompanyDTO;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.services.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("PARTE DE GERENCIAMENTO EMPRESA")
@RestController
@RequestMapping(value = "/company")
public class CompanyResource {

    @Autowired
    private CompanyService service;

    @Autowired
    private ClientRepository repository;

    /**
     * Cria um novo Usuario
     * @param obj
     * @return URI
     * @return CompanyDTO
     */
    @ApiOperation(value = "Cria um novo usuario")
    @PostMapping("/createcompany")
    public ResponseEntity<CompanyDTO> create(@Valid @RequestBody Company obj) {
        Company company = service.create(obj);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
