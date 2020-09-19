package pl.akjos.training_manager.training;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class TrainingDTO {

    private Long id;
    @NotEmpty
    private String title;

    private String description;

    @NotNull
    private Double price;

    private Integer quantityAvailable;

    private Integer trainingDays;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataStart;

    @NotEmpty
    private List<String> department;

}
