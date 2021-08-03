package bloodsugar.clients;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.After;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientCommand {

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Name of client", example = "Jane Doe")
    private String name;

    @NotBlank
    @Size(max = 9)
    @TajNumberValidation
    @Schema(description = "TAJ number of client", example = "888777666")
    private String TajNumber;

    @Future
    @Schema(description = "Expected pregnancy termination date of client", example = "2021-09-30")
    private LocalDate terminationDate;
}
