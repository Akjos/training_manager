package pl.akjos.training_manager.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        if (bindingResult.hasErrors()) {
            model.addAttribute("departmentList", trainingService.getDepartmentList());
            return "/training/manage/edit";
        }
        trainingService.updateTrainingToDb(trainingDTO);
        return "redirect:/training/manage/list";
    }
}
