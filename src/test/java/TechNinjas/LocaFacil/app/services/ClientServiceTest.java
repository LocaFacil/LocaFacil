package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.repositories.ClientRepository;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ClientServiceTest {

    private static final Integer ID = 1;
    private static final String NAME = "Vinicius Admin";
    //private static final String CPF = "488.484.130-13";
    //private static final String PHONE = "";
    private static final String EMAIL = "admin@mail.com";
    private static final String PASSWORD = "1313";
    //private static final boolean TERMSUSE = true;
    private Client client;

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        client = new Client(ID, NAME, EMAIL, PASSWORD);
    }

    @Test
    public void deveRetornarUsuario_QuandoChamarFindByIdComIdValidoTest() {
        Mockito.when(clientRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(client));
        Client response = clientService.findById(ID);

        Assertions.assertEquals(client.toString(), response.toString());
        Assertions.assertNotNull(response);
    }

    @Test
    public void deveRetornarErro_QuandoChamarFindByIdComIdInvalidoTest() {
        try {
            Mockito.when(clientService.findById(Mockito.anyInt()))
                    .thenThrow(new ObjectNotFoundException(
                            "Objeto não encontrado! Id: " + ID + ", Tipo: " + Client.class.getSimpleName()));
        } catch (ObjectNotFoundException ex) {
            Assertions.assertEquals(ex.getMessage(), "Objeto não encontrado! Id: " + 0 + ", Tipo: " + Client.class.getSimpleName());
        }
    }

    @Test
    public void deveRetornarListaDeUsuario_QuandoChamarFindAllTest() {
        List<Client> list = Collections.singletonList(client);
        Mockito.when(clientRepository.findAll()).thenReturn(list);

        List<Client> response = clientService.findAll();
        Assertions.assertEquals(response.get(0).toString(), list.get(0).toString());
    }

    @Test
    public void deveRetornarUsuario_QuandoCreateForChamadoTest() {
        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);
        Client response = clientService.create(client);
        Assertions.assertEquals(client.toString(), response.toString());
    }

    @Test
    public void deveRetornarUsuario_QuandoUpdateForChamadoTest() {
        Mockito.when(clientRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);

        Client response = clientService.update(1, client);
        Assertions.assertEquals(client.toString(), response.toString());
    }

}
