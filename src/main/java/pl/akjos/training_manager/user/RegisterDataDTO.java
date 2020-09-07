package pl.akjos.training_manager.user;

import lombok.Data;

@Data
class RegisterDataDTO {

    private String username;
    private String password;
    private String email;
    private String role;
    private String department;
}
