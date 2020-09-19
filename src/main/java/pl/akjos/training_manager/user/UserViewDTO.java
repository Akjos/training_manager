package pl.akjos.training_manager.user;

import lombok.Data;

@Data
public class UserViewDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String department;

    public UserViewDTO(Long id, String firstName, String lastName, String email, String role, String department) {
        this.id = id;
        this.name = firstName + " " + lastName;
        this.email = email;
        this.role = role.replace("ROLE", "").replace("_", " ").toLowerCase();
        this.department = department;
    }
}
