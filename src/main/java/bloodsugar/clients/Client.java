package bloodsugar.clients;

import bloodsugar.measurements.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, name = "taj_number")
    private String TajNumber;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @OrderBy("time")
    private List<Measurement> measurements = new ArrayList<>();

    public Client(String name, String tajNumber, LocalDate terminationDate) {
        this.name = name;
        TajNumber = tajNumber;
        this.terminationDate = terminationDate;
    }

    public void addMeasurement (Measurement measurement){
        measurements.add(measurement);
        measurement.setClient(this);
    }
}
