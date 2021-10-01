package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.dtos.DumpsterDTO;
import TechNinjas.LocaFacil.app.repositories.DumpsterRepository;
import TechNinjas.LocaFacil.app.services.DumpsterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("Dumpster Management System")
@RestController
@RequestMapping(value = "/dumpster")
public class DumpsterResource {

    @Autowired
    DumpsterRepository dumpsterRepository;

    @Autowired
    DumpsterService service;

    @ApiOperation(value = "Create dumpster")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created dumpster"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DumpsterDTO> create(@Valid @RequestBody Dumpster dumpster) {
        //Vou ter que vincular o id da conta empresa conectada com a caçamba criada
        Dumpster dump = service.create(dumpster);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Find dumpster by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "found dumpster"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DumpsterDTO> findById(@PathVariable Integer id) {
        Dumpster dump = service.findById(id);
        return ResponseEntity.ok().body(new DumpsterDTO(dump));
    }

    @ApiOperation(value = "Update dumpster by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated dumpster"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DumpsterDTO> update(@PathVariable Integer id, @Valid @RequestBody Dumpster obj) {
        Dumpster newObj = service.update(id, obj);
        return ResponseEntity.ok().body(new DumpsterDTO(newObj));
    }

    @ApiOperation(value = "Delete dumpster by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted dumpster"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
