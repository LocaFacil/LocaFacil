package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.dtos.ClientDTO;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.services.ClientService;
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

@Api("Client Management")
@RestController
@RequestMapping(value = "/user")
public class ClientResource {

    @Autowired
    private ClientService service;

    @Autowired
    private ClientRepository repository;

    /**
     * Busca um Usuario pelo id
     * @param id
     * @return UsuarioDTO
     */
    @ApiOperation(value = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Finded user"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) {
        Client client = service.findById(id);
        return ResponseEntity.ok().body(new ClientDTO(client));
    }

    /**
     * Lista todos os Usuarios do banco
     * @return List<UsuarioDTO>
     */
    @ApiOperation(value = "Return user list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned user list"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> list = service.findAll();
        List<ClientDTO> listDTO = list.stream().map(obj -> new ClientDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    /**
     * Cria um novo Usuario
     * @param obj
     * @return URI
     * @return UsuarioDTO
     */
    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created the user"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PostMapping("/createuser")
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody Client obj) {
        Client client = service.create(obj);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Atualiza um Usuario
     * @param id
     * @param obj
     * @return usuarioDTO
     */
    @ApiOperation(value = "Update user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated user"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Integer id, @Valid @RequestBody Client obj) {
    	Client newObj = service.update(id, obj);
    	return ResponseEntity.ok().body(new ClientDTO(newObj));
    }

    /**
     * Deleta um Usuario por id
      * @param id do Usuario a ser deletado
     * @return noContent
     */
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

    @ApiOperation(value = "Check user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Check user"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping(value = "/check/{id}")
    public ResponseEntity<Boolean> checkUser(@PathVariable Integer id){
        Boolean client = service.checkUser(id);
        return ResponseEntity.ok().body(client);
    }

    @PutMapping(value = "/check/{id}")
    public ResponseEntity<ClientDTO> updateCheck(@PathVariable Integer id, @Valid @RequestBody Client obj) {
        Client newObj = service.updateCheck(id, obj);
        return ResponseEntity.ok().body(new ClientDTO(newObj));
    }
}