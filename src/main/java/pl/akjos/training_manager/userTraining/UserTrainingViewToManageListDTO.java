package pl.akjos.training_manager.userTraining;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserTrainingViewToManageListDTO {

    private Long id;

    private String userName;

    private String title;

    private Double price;

    private Integer trainingDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataStart;

    public UserTrainingViewToManageListDTO(Long id, String userName, String title, Double price, Integer trainingDays, LocalDate dataStart) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.price = price;
        this.trainingDays = trainingDays;
        this.dataStart = dataStart;
    }
}
