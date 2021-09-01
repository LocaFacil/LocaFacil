package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Usuario;
import TechNinjas.LocaFacil.app.models.dtos.UsuarioDTO;
import TechNinjas.LocaFacil.app.repositories.UsuarioRepository;
import TechNinjas.LocaFacil.app.services.UsuarioService;
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

@Api("API SISTEMA LOGIN")
@RestController
@RequestMapping(value = "/user")
public class UsuarioResource {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;

    /**
     * Busca um Usuario pelo id
     * @param id
     * @return UsuarioDTO
     */
    @ApiOperation(value = "Encontrar por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        Usuario usuario = service.findById(id);
        return ResponseEntity.ok().body(new UsuarioDTO(usuario));
    }

    /**
     * Lista todos os Usuarios do banco
     * @return List<UsuarioDTO>
     */

    @ApiOperation(value = "Retorna lista de usuarios")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<Usuario> list = service.findAll();
        List<UsuarioDTO> listDTO = list.stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());
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
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody Usuario obj) {
        Usuario usuario = service.create(obj);
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
    public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @Valid @RequestBody Usuario obj) {
    	Usuario newObj = service.update(id, obj);
    	return ResponseEntity.ok().body(new UsuarioDTO(newObj));
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

//    @ApiOperation(value = "Reset Password")
//    @GetMapping("/resetPassword")
//    public String resetPassword(@RequestParam String email) {
//        Optional<Usuario> optUser = repository.findByEmail(email);
//        if (optUser.isEmpty()){
//            return "Email invalido ou em branco";
//        }
//        Usuario user = optUser.get();
//        return  "Senha solicitada: " + user.getSenha();
//    }
}