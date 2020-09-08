package pl.akjos.training_manager.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
class UserRegisterDTO {

    @NotEmpty
    @Size(min = 3, max = 25)
    private String username;
    @NotEmpty
    @Size(min = 7, max = 25)
    private String password;
    @NotEmpty
    @Email
    private String email;
    private String role;
    private String department;
}
