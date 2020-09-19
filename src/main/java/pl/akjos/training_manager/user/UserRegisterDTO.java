package pl.akjos.training_manager.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserRegisterDTO {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 25)
    private String username;

    @NotEmpty
    @Size(min = 7, max = 25)
    private String password;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String role;

    private String department;

    public UserRegisterDTO(Long id, @NotEmpty @Size(min = 3, max = 25) String username, @NotEmpty @Email String email, @NotEmpty String firstName, @NotEmpty String lastName, String role, String department) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
    }
}
