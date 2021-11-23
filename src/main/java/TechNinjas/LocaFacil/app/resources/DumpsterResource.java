package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.dtos.DumpsterDTO;
import TechNinjas.LocaFacil.app.repositories.CompanyRepository;
import TechNinjas.LocaFacil.app.repositories.DumpsterCustomRepository;
import TechNinjas.LocaFacil.app.repositories.DumpsterRepository;
import TechNinjas.LocaFacil.app.repositories.DumpsterStatusRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Api("Dumpster Management System")
@RestController
@RequestMapping(value = "/dumpster")
public class DumpsterResource {

    @Autowired
    DumpsterRepository dumpsterRepository;

    @Autowired
    DumpsterCustomRepository dumpsterCustomRepository;

    @Autowired
    DumpsterStatusRepository dumpsterStatusRepository;

    @Autowired
    CompanyRepository companyRepository;

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
        Dumpster dump = service.create(dumpster);
        if(dump != null){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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
        if(dump != null){
            return ResponseEntity.ok().body(new DumpsterDTO(dump));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "Find dumpster by filter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "found dumpster"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping(value = "/filter")
    public List<DumpsterDTO> findDumpsterByCompanyId(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "company_id", required = false) Integer company_id,
            @RequestParam(value = "statusid", required = false) Integer statusid){
        return this.dumpsterCustomRepository.find(id, price, size, company_id, statusid)
                .stream()
                .map(obj -> new DumpsterDTO(obj))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/status/{statusid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<DumpsterDTO>> getDumpsterByStatus(@PathVariable Integer statusid) {
        List<Dumpster> dumplist = service.getDumpsterByStatus(statusid);
        List<DumpsterDTO> listDTO = dumplist.stream().map(obj -> new DumpsterDTO(obj)).collect(Collectors.toList());
        if(dumplist != null){
            return ResponseEntity.ok().body(listDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<DumpsterDTO>> findAll() {
        List<Dumpster> list = service.findAll();
        List<DumpsterDTO> listDTO = list.stream().map(obj -> new DumpsterDTO(obj)).collect(Collectors.toList());
        if(list != null){
            return ResponseEntity.ok().body(listDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/status/available")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<DumpsterDTO>> findAllStatus() {
        List<Dumpster> list = dumpsterRepository.getDumpsterByStatusId();
        List<DumpsterDTO> listDTO = list.stream().map(obj -> new DumpsterDTO(obj)).collect(Collectors.toList());
        if(list != null){
            return ResponseEntity.ok().body(listDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
        if(newObj != null){
            return ResponseEntity.ok().body(new DumpsterDTO(newObj));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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
