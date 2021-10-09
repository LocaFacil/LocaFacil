package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.Request;
import TechNinjas.LocaFacil.app.models.dtos.RequestDTO;
import TechNinjas.LocaFacil.app.models.enums.Status;
import TechNinjas.LocaFacil.app.repositories.RequestRepository;
import TechNinjas.LocaFacil.app.services.RequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Dumpster Request System")
@RestController
@RequestMapping(value = "/request")
public class RequestResource {

    @Autowired
    RequestRepository repository;

    @Autowired
    RequestService service;

    @ApiOperation(value = "Create request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created request"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PostMapping("/create")
    public ResponseEntity<RequestDTO> create(@Valid @RequestBody Request request, Dumpster dumpster) {
        if(dumpster.getStatus().contains(Status.AVAILABLE)){
            Request req = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
