package bloodsugar.measurements;

import bloodsugar.clients.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDto {

    private Long id;

    private LocalDateTime time;

    private MeasurementType type;

    private long result;

}
