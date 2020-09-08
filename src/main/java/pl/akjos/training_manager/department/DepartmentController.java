package pl.akjos.training_manager.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public String showDepartmentListAndAddForm(Model model) {
        model.addAttribute("department", new DepartmentDTO());
        model.addAttribute("departmentList", departmentService.getDepartmentList());
        return "/department/department";
    }

    @PostMapping("/add")
    public String addDepartment(@ModelAttribute("department") @Valid DepartmentDTO department, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departmentList", departmentService.getDepartmentList());
            return "/department/department";
        }
        departmentService.addDepartment(department);
        return "redirect:/department";
    }

}
