package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Company;
import TechNinjas.LocaFacil.app.models.dtos.CompanyDTO;
import TechNinjas.LocaFacil.app.repositories.CompanyRepository;
import TechNinjas.LocaFacil.app.services.CompanyService;
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

@Api("Company Management")
@RestController
@RequestMapping(value = "/company")
public class CompanyResource {

    @Autowired
    private CompanyService service;

    @Autowired
    private CompanyRepository repository;

    @ApiOperation(value = "Create a new company-type user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created a new company-type user"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PostMapping("/createcompany")
    public ResponseEntity<CompanyDTO> create(@Valid @RequestBody Company obj) {
        Company company = service.create(obj);
        if(company != null){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Finded user"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDTO> findById(@PathVariable Integer id) {
        Company company = service.findById(id);
        if(company != null){
            return ResponseEntity.ok().body(new CompanyDTO(company));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "Update user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated user"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<CompanyDTO> update(@PathVariable Integer id, @Valid @RequestBody Company obj) {
        Company newObj = service.update(id, obj);
        if(newObj != null){
            return ResponseEntity.ok().body(new CompanyDTO(newObj));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted user"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
