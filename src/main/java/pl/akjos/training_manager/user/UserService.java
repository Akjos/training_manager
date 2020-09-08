package pl.akjos.training_manager.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.akjos.training_manager.domain.model.Department;
import pl.akjos.training_manager.domain.model.User;
import pl.akjos.training_manager.domain.repositories.DepartmentRepository;
import pl.akjos.training_manager.domain.repositories.RoleRepository;
import pl.akjos.training_manager.domain.repositories.UserRepository;
import pl.akjos.training_manager.utils.SecurityUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public void registerNewUserToDB(UserRegisterDTO userData) {
        String userRole = SecurityUtils.getUserRole();
        User user = new User();
        if (userRole.equals("ROLE_TEAM_LEADER")) {
            String userName = SecurityUtils.getUsername();
            User loggedUser = userRepository.getByUsername(userName);
            log.debug("Logged user: {}", loggedUser);
            user.setRole(roleRepository.getByName("ROLE_WORKER"));
//            tak czy lepiej wczytać z repo?
            user.setDepartment(loggedUser.getDepartment());
//            user.setDepartment(departmentRepository.getByName(loggedUser.getDepartment().getName()));

        } else {
            user.setRole(roleRepository.getByName(userData.getRole()));
            user.setDepartment(departmentRepository.getByName(userData.getDepartment()));
        }
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setActive(true);
        log.debug("Save user: {}", user);
        userRepository.save(user);
    }

    //to wykopać do jakiejś fasady department używam tego conajmniej w 2 miejscach ?
    public List<String> getDepartmentList() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList.stream().
                map(Department::getName).
                collect(Collectors.toList());
    }

    public List<UserViewDTO> getUserList() {
        List<User> userList = userRepository.findAll();
        return userList.stream().
                map(this::convertUserToUserDTO).
                collect(Collectors.toList());

    }

    private UserViewDTO convertUserToUserDTO(User e) {
        log.debug("User: {}", e);
        UserViewDTO userDTO = new UserViewDTO();
        userDTO.setUsername(e.getUsername());
        userDTO.setEmail(e.getEmail());
        userDTO.setRole(e.getRole().getName());
        if (e.getDepartment() != null) {
            userDTO.setDepartment(e.getDepartment().getName());
        }
        return userDTO;
    }

}
