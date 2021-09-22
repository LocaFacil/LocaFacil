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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DumpsterDTO> create(@Valid @RequestBody Dumpster dumpster) {
        Dumpster dump = service.create(dumpster);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Encontrar a por caçamba por ID")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DumpsterDTO> findById(@PathVariable Integer id) {
        Dumpster dump = service.findById(id);
        return ResponseEntity.ok().body(new DumpsterDTO(dump));
    }

    @ApiOperation(value = "Atualiza a caçamba por id")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DumpsterDTO> update(@PathVariable Integer id, @Valid @RequestBody Dumpster obj) {
        Dumpster newObj = service.update(id, obj);
        return ResponseEntity.ok().body(new DumpsterDTO(newObj));
    }

    @ApiOperation(value = "Deletar a caçamba por id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
