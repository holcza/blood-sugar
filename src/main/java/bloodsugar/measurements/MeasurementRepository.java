package bloodsugar.measurements;

import bloodsugar.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
}
