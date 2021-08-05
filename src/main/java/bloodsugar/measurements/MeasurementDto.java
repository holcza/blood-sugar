package bloodsugar.measurements;
import bloodsugar.clients.ClientDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private MeasurementType type;

    private LocalDateTime time;

    private double result;

    @JsonBackReference
    private ClientDto client;

}
