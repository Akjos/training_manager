package pl.akjos.training_manager.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.akjos.training_manager.utils.SecurityUtils;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/manage/add")
    public String prepareAddTrainingForm(Model model) {
        model.addAttribute("training", new TrainingDTO());
        model.addAttribute("departmentList", trainingService.getDepartmentList());
        return "/training/manage/add";
    }

    @PostMapping("/manage/add")
    public String addTraining(@ModelAttribute("training") @Valid TrainingDTO trainingDTO, BindingResult bindingResult, Model model) {
        if (SecurityUtils.getUserRole().equals("ROLE_MANAGER") && trainingDTO.getDepartment().isEmpty()) {
            bindingResult.rejectValue("department", "error.department", "This field can't by empty");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("departmentList", trainingService.getDepartmentList());
            return "/training/manage/add";
        }
        trainingService.saveNewTrainingToDb(trainingDTO);
        return "redirect:/training/manage/list";
    }

    @GetMapping("/manage/list")
    public String prepareTrainingList(Model model) {
        model.addAttribute("trainingList", trainingService.getTrainingList());
        return "/training/manage/list";
    }

    @GetMapping("/manage/edit/{id}")
    public String prepareTrainingFormToEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("training", trainingService.getTrainingById(id));
        model.addAttribute("departmentList", trainingService.getDepartmentList());
        return "/training/manage/edit";
    }

    @PostMapping("/manage/edit")
    public String editTraining(@ModelAttribute("training") @Valid TrainingDTO trainingDTO, BindingResult bindingResult, Model model) {
        if (SecurityUtils.getUserRole().equals("ROLE_MANAGER") && trainingDTO.getDepartment().isEmpty()) {
            bindingResult.rejectValue("department", "error.department", "This field can't by empty");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("departmentList", trainingService.getDepartmentList());
            return "/training/manage/edit";
        }
        trainingService.updateTrainingToDb(trainingDTO);
        return "redirect:/training/manage/list";
    }

    @GetMapping("/manage/disable/{id}")
    public String confirmDisableTraining(@PathVariable("id") Long id, Model model) {
        TrainingDTO training = trainingService.getTrainingById(id);
        model.addAttribute("training", training);
        return "/training/manage/disable";
    }

    @PostMapping("/manage/disable")
    public String disableTraining(TrainingDTO training) {
        trainingService.disable(training.getId());
        return "redirect:/training/manage/list";
    }

    @GetMapping("/user/list")
    public String showAvailableTrainingForUser(Model model) {
        model.addAttribute("trainingList", trainingService.getTrainingListForUser());
        return "training/user/training_to_choose";
    }

    @GetMapping("user/showToSignUp/{id}")
    public String showDetailsTrainingForSignUp(@PathVariable("id") Long id, Model model) {
        model.addAttribute("training", trainingService.getTrainingById(id));
        model.addAttribute("signUp", trainingService.countUserTrainingById(id));
        return "training/user/show_to_sign_up";
    }
}
