package pl.akjos.training_manager.userTraining;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserTrainingDetailsDTO {

    public UserTrainingDetailsDTO(Long id, String title, String description, Integer trainingDays, LocalDate dataStart, boolean acceptByTeamLeader, boolean acceptByManager, boolean denied) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.trainingDays = trainingDays;
        this.dataStart = dataStart;
        this.acceptByTeamLeader = acceptByTeamLeader;
        this.acceptByManager = acceptByManager;
        this.denied = denied;
    }

    private Long id;

    private String title;

    private String description;

    private Integer trainingDays;

    private LocalDate dataStart;

    private boolean acceptByTeamLeader;

    private boolean acceptByManager;

    private boolean denied;
}
