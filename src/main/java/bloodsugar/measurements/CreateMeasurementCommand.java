package bloodsugar.measurements;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMeasurementCommand {

    @Schema(description = "Type of blood sugar measurement", example = "ONE_HOUR_AFTER_DINNER")
    private MeasurementType type;

    @Schema(description = "Time of blood sugar measurement", example = "2021-08-03T10:30")
    private LocalDateTime time;

    @Positive
    @Schema(description = "Result of blood sugar measurement", example = "5.4")
    private long result;
}
