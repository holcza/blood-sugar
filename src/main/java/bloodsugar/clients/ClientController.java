package bloodsugar.clients;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Operations on data of clients")
public class ClientController {

    private ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List of clients", description = "List of clients")
    public List<ClientDto> listClients
            (@RequestParam Optional<String> name, @RequestParam Optional<String> tajNumber, @RequestParam Optional<LocalDate> terminationDate) {
        return service.listClients(name, tajNumber, terminationDate);
    }

    @PostMapping
    @Operation(summary = "Create new client data", description = "Create new client data")
    public ClientDto createClient(@Valid @RequestBody CreateClientCommand command) {
        return service.createClient(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify a client data by id", description = "Modify a client data by id")
    public ClientDto modifyClient(@PathVariable("id") long id, @Valid @RequestBody ModifyClientCommand command) {
        return service.modifyClient(id, command);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a client by id", description = "Find a client by id")
    public ClientDto findClientById(@PathVariable("id") long id) {
        return service.findClientById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client data by id", description = "Delete client data by id")
    public void deleteClient(@PathVariable("id") long id) {
        service.deleteClient(id);
    }

    @DeleteMapping
    @Operation(summary = "Delete all clients", description = "Delete all clients")
    public void deleteAllClients() {
        service.deleteAllClients();
    }
}
