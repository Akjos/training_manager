package pl.akjos.training_manager.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @NotNull
    private String lastName;

    public UserDetails(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
