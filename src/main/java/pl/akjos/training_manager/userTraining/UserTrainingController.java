package pl.akjos.training_manager.userTraining;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.akjos.training_manager.training.TrainingDTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("user_training")
@Slf4j
public class UserTrainingController {
    private final UserTrainingService userTrainingService;

    @PostMapping("/add")
    public String addTrainingToUser(TrainingDTO training) {
        log.debug(training.toString());
        userTrainingService.addTrainingToUser(training.getId());
        return "redirect:/user_training/list";
    }

    @GetMapping("/list")
    public String showTrainingListAssignToUser(Model model) {
        model.addAttribute("trainingList", userTrainingService.getUserTraining());
        return "/user_training/list";
    }

    @GetMapping("details/{id}")
    public String showDetailsTraining(@PathVariable("id") Long userTrainingId, Model model) {
        model.addAttribute("training", userTrainingService.getUserTrainingDetails(userTrainingId));
        return "/user_training/details";
    }
}
