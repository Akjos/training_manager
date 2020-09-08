package pl.akjos.training_manager.department;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class DepartmentDTO {

    @NotEmpty
    @NotNull
    private String name;
}
