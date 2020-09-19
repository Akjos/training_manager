package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.akjos.training_manager.domain.model.User;
import pl.akjos.training_manager.user.UserRegisterDTO;
import pl.akjos.training_manager.user.UserViewDTO;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String name);

    @Query("SELECT new pl.akjos.training_manager.user.UserViewDTO(u.id, ud.firstName , ud.lastName, u.email, u.role.name, u.department.name ) FROM User u JOIN u.userDetails ud WHERE u.active = TRUE ORDER BY u.department.name ASC")
    List<UserViewDTO> getActiveUserViewDTOAll();

    @Query("SELECT new pl.akjos.training_manager.user.UserViewDTO(u.id, ud.firstName , ud.lastName, u.email, u.role.name, u.department.name ) FROM User u JOIN u.userDetails ud JOIN u.department d WHERE u.active = TRUE AND d.id = :departmentId AND u.role.name = 'ROLE_WORKER'")
    List<UserViewDTO> getActiveUserViewDTOAllByDepartmentId(Long departmentId);

    @Query("SELECT new pl.akjos.training_manager.user.UserViewDTO(u.id, ud.firstName , ud.lastName, u.email, u.role.name, u.department.name ) FROM User u JOIN u.userDetails ud WHERE u.active = FALSE ORDER BY u.department.name ASC")
    List<UserViewDTO> getNoActiveUserViewDTOAll();

    @Query("SELECT new pl.akjos.training_manager.user.UserViewDTO(u.id, ud.firstName , ud.lastName, u.email, u.role.name, u.department.name ) FROM User u JOIN u.userDetails ud JOIN u.department d WHERE u.active = FALSE AND d.id = :departmentId AND u.role.name = 'ROLE_WORKER'")
    List<UserViewDTO> getNoActiveUserViewDTOAllByDepartmentId(Long departmentId);

    @Query("SELECT new pl.akjos.training_manager.user.UserRegisterDTO(u.id, u.username, u.email, u.userDetails.firstName, u.userDetails.lastName, u.role.name, u.department.name) FROM User u WHERE u.id = :id")
    UserRegisterDTO getUserRegisterDTOById(Long id);

    @Query("SELECT new pl.akjos.training_manager.user.UserViewDTO(u.id, ud.firstName , ud.lastName, u.email, u.role.name, u.department.name ) FROM User u JOIN u.userDetails ud WHERE u.active = TRUE AND u.id = :id")
    UserViewDTO getUserView(Long id);
}
