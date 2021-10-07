package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.dtos.ClientDTO;
import TechNinjas.LocaFacil.app.services.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ClientResourceTest {

    private static final Integer ID = 1;
    private static final String NAME = "";
    //private static final String CPF = "";
    private static final String EMAIL = "";
    //private static final String PHONE = "";
    private static final String PASSWORD = "";
    //private static final boolean TERMSUSE = true;
    private Client client;

    @InjectMocks
    private ClientResource usuarioResource;

    @Mock
    private ClientService clientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        client = new Client(ID, NAME, EMAIL, PASSWORD);
    }

    @Test
    public void deveRetornarUsuarioDTO_QuandoChamarFindByIdTest() {
        Mockito.when(clientService.findById(Mockito.anyInt())).thenReturn(client);
        ResponseEntity<ClientDTO> response = usuarioResource.findById(ID);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody().getId(), client.getId());
        //Assertions.assertEquals(response.getBody().getCpf(), usuario.getCpf());
        Assertions.assertNotNull(response);
    }

    @Test
    public void deveRetornarListaDeUsuarioDTO_QuandoChamarFindAllTest() {
        List<Client> list = Collections.singletonList(client);

        Mockito.when(clientService.findAll()).thenReturn(list);
        ResponseEntity<List<ClientDTO>> response = usuarioResource.findAll();

        Assertions.assertEquals(list.get(0).getId(), response.getBody().get(0).getId());
        Assertions.assertEquals(ClientDTO.class.getSimpleName(), response.getBody().get(0).getClass().getSimpleName());
        Assertions.assertEquals(list.size(), response.getBody().size());
        Assertions.assertEquals(list.stream().count(), response.getBody().stream().count());
    }

    @Test
    public void deveRetornarStatus201_QuandoChamarCreateTest() {
        Mockito.when(clientService.create(Mockito.any())).thenReturn(client);
        ResponseEntity<ClientDTO> response = usuarioResource.create(client);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void deveRetornarStatus200_QuandoChamarUpdateTest() {
        Mockito.when(clientService.update(Mockito.any(), Mockito.any())).thenReturn(client);

        ResponseEntity<ClientDTO> response = usuarioResource.update(ID, client);

        Assertions.assertEquals(ClientDTO.class, response.getBody().getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(client.getId(), response.getBody().getId());
        //Assertions.assertEquals(usuario.getCpf(), response.getBody().getCpf());
        Assertions.assertEquals(client.getPassword(), response.getBody().getPassword());
    }

    @Test
    public void deveRetornarStatus204_QuandoChamarDeleteTest() {
        Mockito.doNothing().when(clientService).delete(Mockito.any());
        ResponseEntity<Void> response = usuarioResource.delete(ID);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}






















