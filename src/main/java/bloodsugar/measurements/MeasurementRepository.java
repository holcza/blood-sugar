package bloodsugar.measurements;

import bloodsugar.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    List<Measurement> findMeasurementByClient_Id(long id);

    @Query(value = "select m from Measurement m join fetch m.client c where c.id=:cId and m.id=:mId")
    Measurement findMeasurementByClient_IdAndId(long cId, long mId);
}
