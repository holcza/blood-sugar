package bloodsugar.clients;

import bloodsugar.measurements.Measurement;
import bloodsugar.measurements.MeasurementDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;

    private String name;

    private String TajNumber;

    private LocalDate terminationDate;

    private List<MeasurementDto> measurements;
}
