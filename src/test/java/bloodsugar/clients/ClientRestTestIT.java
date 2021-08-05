package bloodsugar.clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Status;
import org.zalando.problem.Problem;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientRestTestIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    ClientService service;

    @BeforeEach
    void init() {
        service.deleteAllClients();
    }

    @Test
    @DisplayName(value = "Test creating two clients and listing them")
    void testCreateAndListClients() {
        template.postForObject("/api/clients",
                new CreateClientCommand("Jane Doe", "111222333", LocalDate.of(2021, 9, 30))
                , ClientDto.class);
        template.postForObject("/api/clients",
                new CreateClientCommand("Ann Doe", "111222555", LocalDate.of(2021, 10, 05))
                , ClientDto.class);

        List<ClientDto> clients = template.exchange(
                "/api/clients", HttpMethod.GET, null, new ParameterizedTypeReference<List<ClientDto>>() {
                })
                .getBody();

        assertThat(clients).extracting(ClientDto::getName)
                .containsExactly("Jane Doe", "Ann Doe");

    }

    @Test
    @DisplayName(value = "Test creating and modifying a client")
    void CreateAndModifyClientById() {
        Long id = template.postForObject("/api/clients",
                new CreateClientCommand("Jane Doe", "111222333", LocalDate.of(2021, 9, 30))
                , ClientDto.class).getId();

        ClientDto client = template.exchange(
                "/api/clients/" + id, HttpMethod.GET, null, ClientDto.class)
                .getBody();

        assertEquals("Jane Doe", client.getName());

        template.put("/api/clients/" + id,
                new ModifyClientCommand("Ann Doe", "111222333", LocalDate.of(2021, 9, 30)));

        ClientDto clientModified = template.exchange(
                "/api/clients/" + id, HttpMethod.GET, null, ClientDto.class)
                .getBody();

        assertEquals("Ann Doe", clientModified.getName());
    }

    @Test
    @DisplayName(value = "Test creating and deleting a client")
    void CreateAndDeleteClientById() {
        Long id = template.postForObject("/api/clients",
                new CreateClientCommand("Jane Doe", "111222333", LocalDate.of(2021, 9, 30))
                , ClientDto.class).getId();


        template.delete("/api/clients/" + id);
        assertNull(template.exchange(
                "/api/clients" + id, HttpMethod.GET, null, ClientDto.class).getBody().getName());
    }

    @Test
    @DisplayName(value = "Test creating a client with invalid name")
    void testCreateWithInvalidName() {
        Problem problem = template.postForObject("/api/clients",
                new CreateClientCommand(" ", "111222333", LocalDate.of(2021, 9, 30))
                , Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());

    }

    @Test
    @DisplayName(value = "Test creating a client with invalid TAJ number")
    void testCreateWithInvalidTajNumber() {
        Problem problem= template.postForObject("/api/clients",
                new CreateClientCommand("Jane Doe", "111222333A", LocalDate.of(2021, 9, 30))
                , Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());

    }

    @Test
    @DisplayName(value = "Test creating a client with invalid pregnancy termination date")
    void testCreateWithInvalidTerminationDate() {
        Problem problem = template.postForObject("/api/clients",
                new CreateClientCommand("Jane Doe", "111222333", LocalDate.of(2021, 5, 30))
                , Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());

    }

    @Test
    @DisplayName(value = "Test querying a client not exists")
    void testQueryClientNotExists() {
        Problem result = template.getForObject("/api/clients/1", Problem.class);
        assertEquals(URI.create("clients/client-not-found"),result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());


    }

}
