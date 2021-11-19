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
import java.util.List;
import java.util.stream.Collectors;

@Api("Dumpster Request System")
@RestController
@RequestMapping(value = "/request")
public class RequestResource {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    RequestService service;

    @ApiOperation(value = "Find request by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "found request"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RequestDTO> findById(@PathVariable Integer id) {
        Request req = service.findById(id);
        return ResponseEntity.ok().body(new RequestDTO(req));
    }

    @ApiOperation(value = "Find requests by client id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "found requests"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping(value = "/all/{id}")
    public ResponseEntity<List<RequestDTO>> findAllClientById(@PathVariable Integer id) {
        List<Request> list = service.findAllClientById(id);
        List<RequestDTO> listDTO = list.stream().map(obj -> new RequestDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
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

    @ApiOperation(value = "Liberate Dumpster by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liberated dumpster"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PutMapping(value = "/dumpsterLiberate/{id}")
    public ResponseEntity<RequestDTO> liberateUpdate(@PathVariable Integer id, @Valid @RequestBody Request obj) {
        Request newObj = service.liberateUpdate(id, obj);
        return ResponseEntity.ok().body(new RequestDTO(newObj));
    }

    @ApiOperation(value = "Deliver Dumpster by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delivered dumpster"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PutMapping(value = "/deliverUpdate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RequestDTO> deliverUpdate(@PathVariable Integer id, @Valid @RequestBody Request obj){
        Request newObj = service.deliverUpdate(id, obj);
        return ResponseEntity.ok().body(new RequestDTO(newObj));
    }

    @ApiOperation(value = "Retreat Dumpster by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retreated dumpster"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PutMapping(value = "/retreatUpdate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RequestDTO> retreatUpdate(@PathVariable Integer id, @Valid @RequestBody Request obj){
        Request newObj = service.retreatUpdate(id, obj);
        return ResponseEntity.ok().body(new RequestDTO(newObj));
    }

    @GetMapping(value = "/lists")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<RequestDTO>> listDeliverAndRetreat() {
        List<Request> list = service.findAllDeliversAndRetreat();
        List<RequestDTO> listDTO = list.stream().map(obj -> new RequestDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
}
