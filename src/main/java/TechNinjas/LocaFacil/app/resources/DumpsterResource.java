package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.dtos.DumpsterDTO;
import TechNinjas.LocaFacil.app.repositories.DumpsterRepository;
import TechNinjas.LocaFacil.app.services.DumpsterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("SISTEMA REQUISIÇÃO DE CAÇAMBA")
@RestController
@RequestMapping(value = "/dumpster")
public class DumpsterResource {

    @Autowired
    DumpsterRepository dumpsterRepository;

    @Autowired
    DumpsterService service;

    @ApiOperation(value = "Cria caçamba")
    @PostMapping("/create")
    public ResponseEntity<DumpsterDTO> create(@Valid @RequestBody Dumpster dumpster) {
        Dumpster dump = service.create(dumpster);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Deleta caçamba por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
