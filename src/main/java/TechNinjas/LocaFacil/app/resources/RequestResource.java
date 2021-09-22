package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Request;
import TechNinjas.LocaFacil.app.models.dtos.RequestDTO;
import TechNinjas.LocaFacil.app.repositories.RequestRepository;
import TechNinjas.LocaFacil.app.services.RequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("PARTE DE LOCAÇÃO DE CAÇAMBA")
@RestController
@RequestMapping(value = "/request")
public class RequestResource {

    @Autowired
    RequestRepository repository;

    @Autowired
    RequestService service;

    @ApiOperation(value = "Cria caçamba")
    @PostMapping("/create")
    public ResponseEntity<RequestDTO> create(@Valid @RequestBody Request request) {
        Request req = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
