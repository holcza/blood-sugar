package bloodsugar.measurements;

import bloodsugar.clients.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeasurementService {

    private MeasurementRepository repo;

    private ModelMapper modelMapper;

    private ClientService clientService;

    public List<MeasurementDto> listMeasurements(
            long clientId, Optional<MeasurementType> type, Optional<LocalDateTime> time, Optional<Long> result) {
        clientService.findClientById(clientId);
        return repo.findMeasurementByClient_Id(clientId).stream()
                .filter(t -> type.isEmpty() || t.getType().equals(type.get()))
                .filter(t -> time.isEmpty() || t.getTime().isEqual(time.get()))
                .filter(t -> result.isEmpty() || t.getResult() == result.get())
                .map(m -> modelMapper.map(m, MeasurementDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public MeasurementDto createMeasurement(long clientId, CreateMeasurementCommand command) {
        Measurement measurement = new Measurement(command.getType(), command.getTime(), command.getResult());

        repo.save(measurement);

        clientService.addMeasurementtoClientById(clientId,measurement);

        return modelMapper.map(measurement, MeasurementDto.class);
    }

    @Transactional
    public MeasurementDto modifyMeasurement(long cId, long mId, ModifyMeasurementCommand command) {
        Measurement measurement = repo.findMeasurementByClient_IdAndId(cId, mId);

        measurement.setType(command.getType());
        measurement.setTime(command.getTime());
        measurement.setResult(command.getResult());
        return modelMapper.map(measurement, MeasurementDto.class);
    }


    public MeasurementDto findMeasurementById(long cId, long mId) {
        Measurement measurement = repo.findMeasurementByClient_IdAndId(cId, mId);

        return modelMapper.map(measurement, MeasurementDto.class);
    }

    public void deleteMeasurementById(long cId, long mId) {
        Measurement measurement = repo.findMeasurementByClient_IdAndId(cId, mId);

        repo.delete(measurement);
    }

    public void deleteAllMeasurementsByClient(long id) {

        List<Measurement> measurements = repo.findMeasurementByClient_Id(id);

        repo.deleteAllInBatch(measurements);
    }
}
