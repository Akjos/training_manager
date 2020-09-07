package pl.akjos.training_manager.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.akjos.training_manager.domain.repositories.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final RegisterService registerService;

    @GetMapping("/list")
    public String showUserList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/register")
    public String prepareRegisterForm(Model model) {
        model.addAttribute("user", new RegisterDataDTO());
        List<String> departmentList = registerService.getDepartmentList();
        model.addAttribute("departmentList", departmentList);
        return "/user/register";
    }

    @PostMapping("/register")
    public String prepareRegisterForm(@ModelAttribute("user") RegisterDataDTO user) {
        registerService.registerNewUserToDB(user);
        return "redirect:/user/list";
    }


}