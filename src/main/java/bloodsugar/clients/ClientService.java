package bloodsugar.clients;

import bloodsugar.measurements.Measurement;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository repo;

    private ModelMapper modelMapper;


    public List<ClientDto> listClients(Optional<String> name, Optional<String> tajNumber, Optional<LocalDate> terminationDate) {
        return repo.findAll().stream()
                .filter(t -> name.isEmpty() || t.getName().equals(name.get()))
                .filter(t -> tajNumber.isEmpty() || t.getTajNumber().equals(tajNumber.get()))
                .filter(t -> terminationDate.isEmpty() ||
                        t.getTerminationDate().isEqual(terminationDate.get()))
                .map(m -> modelMapper.map(m, ClientDto.class))
                .collect(Collectors.toList());
    }

    public ClientDto createClient(CreateClientCommand command) {

        Client client = new Client(command.getName(), command.getTajNumber(), command.getTerminationDate());

        repo.save(client);
        return modelMapper.map(client, ClientDto.class);
    }

    @Transactional
    public ClientDto modifyClient(long id, ModifyClientCommand command) {

        Client client = repo.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        client.setName(command.getName());
        client.setTajNumber(command.getTajNumber());
        client.setTerminationDate(command.getTerminationDate());
        return modelMapper.map(client, ClientDto.class);
    }

    public ClientDto findClientById(long id) {
        Client client
                = repo.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        return modelMapper.map(client, ClientDto.class);
    }

    public ClientDto addMeasurementtoClientById (long id, Measurement measurement){
        Client client
                = repo.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        client.addMeasurement(measurement);
        return modelMapper.map(client, ClientDto.class);
    }

    public void deleteClient(long id) {
        Client client = repo.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        repo.delete(client);
    }

    public void deleteAllClients() {
        repo.deleteAll();
    }
}
