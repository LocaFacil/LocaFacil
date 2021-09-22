package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.dtos.ClientDTO;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.services.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api("PARTE DE GERENCIAMENTO CLIENTE")
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
    @ApiOperation(value = "Encontrar por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) {
        Client client = service.findById(id);
        return ResponseEntity.ok().body(new ClientDTO(client));
    }

    /**
     * Lista todos os Usuarios do banco
     * @return List<UsuarioDTO>
     */
    @ApiOperation(value = "Retorna lista de usuarios")
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
    @ApiOperation(value = "Cria um novo usuario")
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
    @ApiOperation(value = "Atualiza usuario")
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
    @ApiOperation(value = "Deleta usuario por id")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}