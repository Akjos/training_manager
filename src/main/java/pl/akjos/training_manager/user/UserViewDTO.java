package pl.akjos.training_manager.user;

import lombok.Data;

@Data
public class UserViewDTO {


    private String username;
    private String email;
    private String role;
    private String department;
}
