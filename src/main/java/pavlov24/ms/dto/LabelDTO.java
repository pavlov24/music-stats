package pavlov24.ms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LabelDTO {

    private Long id;
    @NotBlank
    private String name;
}
