package bloodsugar.measurements;

import bloodsugar.clients.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MeasurementType type;

    @Column(name = "measurement_time")
    private LocalDateTime time;

    private double result;

    @ManyToOne
    @ToString.Exclude
    private Client client;

    public Measurement(MeasurementType type, LocalDateTime time, double result) {
        this.type = type;
        this.time = time;
        this.result = result;
    }
}
