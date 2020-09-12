package pl.akjos.training_manager.userTraining;

import lombok.Data;


@Data
public class UserTrainingDTO {

    private String title;

    private Long trainingId;

    private boolean acceptByTeamLeader;

    private boolean acceptByManager;

    private boolean denied;
}
