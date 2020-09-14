package pl.akjos.training_manager.userTraining;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserTrainingDetailsToManageDTO {

    private Long id;

    private String title;

    private String description;

    private Integer trainingDays;

    private Integer quantityAvailable;

    private Double price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataStart;

    public UserTrainingDetailsToManageDTO(Long id, String title, String description, Integer trainingDays, Integer quantityAvailable, Double price, LocalDate dataStart) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.trainingDays = trainingDays;
        this.quantityAvailable = quantityAvailable;
        this.price = price;
        this.dataStart = dataStart;
    }
}
