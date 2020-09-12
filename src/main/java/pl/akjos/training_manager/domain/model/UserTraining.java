package pl.akjos.training_manager.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames ={"user_id", "training_id"}) ,name = "user_training")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class UserTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @JoinColumn(name = "training_id", insertable = false, updatable = false)
    @ManyToOne
    private Training training;

    @Column(name = "training_id")
    private Long trainingId;

    @Column(name = "accept_by_team_leader")
    private boolean acceptByTeamLeader;

    @Column(name = "accept_by_manager")
    private boolean acceptByManager;

    @Column(name = "denied")
    private boolean denied;

    @PrePersist
    public void prePersist() {
        acceptByTeamLeader =false;
        acceptByManager =false;
        denied =false;
    }
}
