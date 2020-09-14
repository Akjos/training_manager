package pl.akjos.training_manager.userTraining;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.akjos.training_manager.training.TrainingDTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("user_training")
@Slf4j
public class UserTrainingController {
    private final UserTrainingService userTrainingService;

    @PostMapping("/add")
    public String addTrainingToUser(TrainingDTO training) {
        userTrainingService.addTrainingToUser(training.getId());
        return "redirect:/user_training/list";
    }

    @GetMapping("/list")
    public String showTrainingListAssignToUser(Model model) {
        model.addAttribute("trainingList", userTrainingService.getUserTraining());
        return "/user_training/list";
    }

    @GetMapping("/details/{id}")
    public String showDetailsTraining(@PathVariable("id") Long userTrainingId, Model model) {
        model.addAttribute("training", userTrainingService.getUserTrainingDetails(userTrainingId));
        return "/user_training/details";
    }

    @GetMapping("/delete/{id}")
    public String prepareToDeleteTrainingFromUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userTrainingId", id);
        return "/user_training/delete_confirm";
    }

    @PostMapping("/delete")
    public String deleteTrainingFromUser(@ModelAttribute("userTrainingId") Long userTrainingId) {
        userTrainingService.deleteUserTrainingById(userTrainingId);
        return "redirect:/user_training/list";
    }

    @GetMapping("/manage/leader/list_to_accept")
    public String prepareTrainingToAcceptListForTeamLeader(Model model) {
        model.addAttribute("trainingList", userTrainingService.getTrainingListToAcceptForTeamLeader());
        return "/user_training/manage/leader/list_to_accept";
    }

    @GetMapping("/manage/details/{id}")
    public String prepareTrainingToAcceptFormForTeamLeader(@PathVariable("id") Long id, Model model) {
        UserTrainingDetailsToManageDTO userTrainingDetailsToManageDTO = userTrainingService.getUserTrainingDetailsForManage(id);
        model.addAttribute("training", userTrainingDetailsToManageDTO);
        model.addAttribute("accept", userTrainingService.getCountAcceptTraining(userTrainingDetailsToManageDTO.getTitle()));
        return "/user_training/manage/details";
    }
}
