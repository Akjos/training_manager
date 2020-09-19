package pl.akjos.training_manager.domain.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private String password;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_details_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UserDetails userDetails;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Department department;

}
