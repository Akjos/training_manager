package pl.akjos.training_manager.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.akjos.training_manager.domain.model.Department;
import pl.akjos.training_manager.domain.model.User;
import pl.akjos.training_manager.domain.model.UserDetails;
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
            user.setDepartment(loggedUser.getDepartment());
        } else {
            user.setRole(roleRepository.getByName(userData.getRole()));
            user.setDepartment(departmentRepository.getByName(userData.getDepartment()));
        }
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setActive(true);
        user.setUserDetails(new UserDetails(userData.getFirstName(), userData.getLastName()));
        log.debug("Save user: {}", user);
        userRepository.save(user);
    }

    public List<String> getDepartmentList() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList.stream().
                map(Department::getName).
                collect(Collectors.toList());
    }

    public List<UserViewDTO> getUserActiveList() {
        String userRole = SecurityUtils.getUserRole();
        if (userRole.equals("ROLE_TEAM_LEADER")) {
            String userName = SecurityUtils.getUsername();
            User loggedUser = userRepository.getByUsername(userName);
            return userRepository.getActiveUserViewDTOAllByDepartmentId(loggedUser.getDepartment().getId());
        } else {
            return userRepository.getActiveUserViewDTOAll();
        }
    }

    public List<UserViewDTO> getUserNoActiveList() {
        String userRole = SecurityUtils.getUserRole();
        if (userRole.equals("ROLE_TEAM_LEADER")) {
            String userName = SecurityUtils.getUsername();
            User loggedUser = userRepository.getByUsername(userName);
            return userRepository.getNoActiveUserViewDTOAllByDepartmentId(loggedUser.getDepartment().getId());
        } else {
            return userRepository.getNoActiveUserViewDTOAll();
        }
    }

    public UserRegisterDTO getUserToEdit(Long id) {
        return userRepository.getUserRegisterDTOById(id);
    }

    public void editUserBySuperior(UserRegisterDTO userDTO) {
        String userRole = SecurityUtils.getUserRole();
        User user = userRepository.findById(userDTO.getId()).get();
        if (!userRole.equals("ROLE_TEAM_LEADER")) {
            user.setRole(roleRepository.getByName(userDTO.getRole()));
            user.setDepartment(departmentRepository.getByName(userDTO.getDepartment()));
        }
        user.getUserDetails().setFirstName(userDTO.getFirstName());
        user.getUserDetails().setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        if (!userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(user);
    }
}
