package pl.akjos.training_manager.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.akjos.training_manager.utils.SecurityUtils;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/list_to_edit")
    public String showUserList(Model model) {
        model.addAttribute("userActiveList", userService.getUserActiveList());
        model.addAttribute("userNoActiveList", userService.getUserNoActiveList());
        return "/user/list";
    }

    @GetMapping("/register")
    public String prepareRegisterForm(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        List<String> departmentList = userService.getDepartmentList();
        model.addAttribute("departmentList", departmentList);
        return "/user/register";
    }

    @PostMapping("/register")
    public String prepareRegisterForm(@ModelAttribute("user") @Valid UserRegisterDTO user, BindingResult bindingResult, Model model) {
        if (SecurityUtils.getUserRole().equals("ROLE_MANAGER") && user.getDepartment() == null) {
            bindingResult.rejectValue("department", "error.department", "This field can't by empty");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("departmentList", userService.getDepartmentList());
            return "/user/register";
        }
        userService.registerNewUserToDB(user);
        return "redirect:/user/list_to_edit";
    }

    @GetMapping("/edit/{id}")
    public String prepareToEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserToEdit(id));
        model.addAttribute("departmentList", userService.getDepartmentList());
        return "/user/edit";
    }

    @PostMapping("/edit")
    public String editUserBySuperior(@ModelAttribute("user") UserRegisterDTO user) {
        userService.editUserBySuperior(user);
        return "redirect:/user/list_to_edit";
    }




}
