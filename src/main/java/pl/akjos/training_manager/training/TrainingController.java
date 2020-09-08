package pl.akjos.training_manager.training;

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
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/manage/add")
    public String prepareAddTrainingForm(Model model) {
        model.addAttribute("training", new TrainingDTO());
        model.addAttribute("departmentList", trainingService.getDepartmentList());
        return "/training/manage/addTraining";
    }

    @PostMapping("/manage/add")
    public String addTraining(@ModelAttribute("training") @Valid TrainingDTO trainingDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("departmentList", trainingService.getDepartmentList());
            return "/training/manage/addTraining";
        }
        trainingService.saveToDb(trainingDTO);
        return "redirect:/";
    }

}
