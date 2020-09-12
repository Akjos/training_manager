package pl.akjos.training_manager.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trainings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "departments")
@EqualsAndHashCode(of = "id")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private Double price;

    @Column(name = "quantity_available")
    private Integer quantityAvailable;

    @Column(name = "training_days")
    private Integer trainingDays;

    @Column(name = "data_start")
    private LocalDate dataStart;

    @ManyToMany
    @JoinTable(name = "training_department",
            joinColumns = @JoinColumn(name = "trainings_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> departments;

}
