package pl.akjos.training_manager.userTraining;

import lombok.Data;


@Data
public class UserTrainingListDTO {

    public UserTrainingListDTO(Long userTrainingId, String title, boolean acceptByTeamLeader, boolean acceptByManager, boolean denied) {
        this.userTrainingId = userTrainingId;
        this.title = title;
        this.acceptByTeamLeader = acceptByTeamLeader;
        this.acceptByManager = acceptByManager;
        this.denied = denied;
    }

    private Long userTrainingId;

    private String title;

    private boolean acceptByTeamLeader;

    private boolean acceptByManager;

    private boolean denied;
}
