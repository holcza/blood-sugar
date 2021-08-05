package bloodsugar.measurements;

import bloodsugar.clients.Client;
import bloodsugar.clients.ClientDto;
import bloodsugar.clients.CreateClientCommand;
import bloodsugar.clients.ModifyClientCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from measurements")
@Sql(statements = "delete from clients")
public class MeasurementRestTestIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    MeasurementService service;

    private Client client;

    @BeforeEach
    void init() {
        client = template.postForObject("/api/clients",
                new CreateClientCommand("Jane Doe", "111222333", LocalDate.of(2021, 9, 30))
                , Client.class);
    }

    @Test
    @DisplayName(value = "Test creating two measurements and listing them")
    void testCreateAndListMeasurements() {
        template.postForObject("/api/clients/" + client.getId() + "/measurements",
                new CreateMeasurementCommand(
                        MeasurementType.BEFORE_LUNCH, LocalDateTime.of(2021, 8, 3, 11, 30), 5.6)
                , MeasurementDto.class);
        template.postForObject("/api/clients/" + client.getId() + "/measurements",
                new CreateMeasurementCommand(
                        MeasurementType.ONE_HOUR_AFTER_LUNCH, LocalDateTime.of(2021, 8, 3, 12, 30), 6.8)
                , MeasurementDto.class);

        List<MeasurementDto> measurements = template.exchange(
                "/api/clients/" + client.getId() + "/measurements",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MeasurementDto>>() {
                })
                .getBody();

        assertThat(measurements).extracting(MeasurementDto::getResult)
                .containsExactly(5.6, 6.8);

    }

    @Test
    @DisplayName(value = "Test creating and modifying a measurement")
    void CreateAndModifyMeasurementById() {
        Long id = template.postForObject("/api/clients/" + client.getId() + "/measurements",
                new CreateMeasurementCommand(
                        MeasurementType.BEFORE_LUNCH, LocalDateTime.of(2021, 8, 3, 11, 30), 5.6)
                , MeasurementDto.class).getId();

        MeasurementDto measurement = template.exchange(
                "/api/clients/" + client.getId() + "/measurements/" + id, HttpMethod.GET, null, MeasurementDto.class)
                .getBody();

        assertEquals(5.6, measurement.getResult());

        template.put("/api/clients/" + client.getId() + "/measurements/" + id,
                new ModifyMeasurementCommand(
                        MeasurementType.ONE_HOUR_AFTER_LUNCH,
                        LocalDateTime.of(2021, 8, 3, 12, 30),
                        6.8));

        MeasurementDto measurementModified = template.exchange(
                "/api/clients/" + client.getId() + "/measurements/" + id,
                HttpMethod.GET,
                null,
                MeasurementDto.class)
                .getBody();

        assertEquals(6.8, measurementModified.getResult());
    }

    @Test
    @DisplayName(value = "Test creating and deleting a measurement")
    void CreateAndDeleteMeasurementById() {
        Long id = template.postForObject("/api/clients/" + client.getId() + "/measurements",
                new CreateMeasurementCommand(
                        MeasurementType.BEFORE_LUNCH, LocalDateTime.of(2021, 8, 3, 11, 30), 5.6)
                , MeasurementDto.class).getId();


        template.delete("/api/clients/" + client.getId() + "/measurements" + id);
        assertNull(template.exchange(
                "/api/clients/" + client.getId() + "/measurements" + id,
                HttpMethod.GET,
                null,
                MeasurementDto.class).getBody().getType());
    }

    @Test
    @DisplayName(value = "Test creating a measurement with invalid result")
    void testCreateWithInvalidResult() {
        Problem result =template.postForObject("/api/clients/" + client.getId() + "/measurements",
                new CreateMeasurementCommand(MeasurementType.BEFORE_LUNCH, LocalDateTime.of(2021, 8, 3, 11, 30), -5.6)
                , Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());

    }


}
