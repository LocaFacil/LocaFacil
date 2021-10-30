package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Request;
import TechNinjas.LocaFacil.app.models.dtos.RequestDTO;
import TechNinjas.LocaFacil.app.repositories.RequestRepository;
import TechNinjas.LocaFacil.app.services.RequestService;
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

@Api("Dumpster Request System")
@RestController
@RequestMapping(value = "/request")
public class RequestResource {

    @Autowired
    RequestRepository repository;

    @Autowired
    RequestService service;

    @ApiOperation(value = "Find request by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "found request"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RequestDTO> findById(@PathVariable Integer id) {
        Request req = service.findById(id);
        return ResponseEntity.ok().body(new RequestDTO(req));
    }

    @ApiOperation(value = "Create request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created request"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PostMapping("/create")
    public ResponseEntity<RequestDTO> create(@Valid @RequestBody Request request) {
            Request req = service.create(request);
            if(req != null){
                return ResponseEntity.ok().body(new RequestDTO(req));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
    }

    @ApiOperation(value = "Update request by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated request"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PutMapping(value = "/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RequestDTO> update(@PathVariable Integer id, @Valid @RequestBody Request obj) {
        Request newObj = service.update(id, obj);
        return ResponseEntity.ok().body(new RequestDTO(newObj));
    }

    @ApiOperation(value = "Delete request by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted request"),
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
