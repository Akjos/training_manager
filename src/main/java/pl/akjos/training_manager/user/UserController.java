package pl.akjos.training_manager.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.akjos.training_manager.domain.repositories.UserRepository;
import pl.akjos.training_manager.utils.SecurityUtils;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
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
    public String prepareRegisterForm(@ModelAttribute("user") @Valid RegisterDataDTO user, BindingResult bindingResult, Model model) {
        if (SecurityUtils.getUserRole().equals("ROLE_MANAGER") && user.getDepartment() == null) {
            bindingResult.rejectValue("department", "error.department", "This field can't by empty");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("departmentList", registerService.getDepartmentList());
            return "/user/register";
        }
        registerService.registerNewUserToDB(user);
        return "redirect:/user/list";
    }


}
