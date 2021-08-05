package bloodsugar.measurements;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients/{id}/measurements")
@Tag(name = "Operations on measurements")
public class MeasurementController {

    private MeasurementService service;

    public MeasurementController(MeasurementService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List of measurements for a client", description = "List of measurements for a client")
    public List<MeasurementDto> listMeasurements
            (@PathVariable("id") long clientId, @RequestParam Optional<MeasurementType> type, @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date) {
        return service.listMeasurements(clientId, type, date);
    }

    @PostMapping
    @Operation(summary = "Create new measurement for a client", description = "Create new measurement for a client")
    public MeasurementDto createMeasurement(@PathVariable("id") long clientId, @Valid @RequestBody CreateMeasurementCommand command) {
        return service.createMeasurement(clientId, command);
    }

    @PutMapping("/{mId}")
    @Operation(summary = "Modify a measurement for a client by id", description = "Modify a measurement for a client by id")
    public MeasurementDto modifyMeasurement(
            @PathVariable("id") long cId, @PathVariable("mId") long mId, @Valid @RequestBody ModifyMeasurementCommand command) {
        return service.modifyMeasurement(cId, mId, command);
    }

    @GetMapping("/{mId}")
    @Operation(summary = "Find a measurement by id", description = "Find a measurement by id")
    public MeasurementDto findMeasurementById(@PathVariable("id") long cId, @PathVariable("mId") long mId) {
        return service.findMeasurementById(cId, mId);
    }

    @DeleteMapping("/{mId}")
    @Operation(summary = "Delete a measurement by id", description = "Delete a measurement by id")
    public void deleteMeasurementById(@PathVariable("id") long cId, @PathVariable("mId") long mId) {
        service.deleteMeasurementById(cId, mId);
    }

    @DeleteMapping
    @Operation(summary = "Delete all measurements of a client", description = "Delete all measurements of a client")
    public void deleteAllMeasurementsByClient(@PathVariable("id") long id) {
        service.deleteAllMeasurementsByClient(id);
    }
}

