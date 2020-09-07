package pl.akjos.training_manager.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String addDepartment(DepartmentDTO department) {
        departmentService.addDepartment(department);
        return "redirect:/department";
    }

}
