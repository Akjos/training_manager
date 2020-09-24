package pl.akjos.training_manager.userTraining;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.akjos.training_manager.department.DepartmentDTO;
import pl.akjos.training_manager.department.DepartmentService;
import pl.akjos.training_manager.training.TrainingDTO;
import pl.akjos.training_manager.utils.SecurityUtils;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("user_training")
@Slf4j
public class UserTrainingController {
    private final UserTrainingService userTrainingService;
    private final DepartmentService departmentService;

//    endpoint for user

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

//    endpoint for leader to manage userTraining

    @GetMapping("/manage/leader/list_to_accept")
    public String prepareTrainingToAcceptListForTeamLeader(Model model) {
        model.addAttribute("trainingList", userTrainingService.getTrainingListToAcceptForTeamLeader());
        return "/user_training/manage/leader/to_accept_list";
    }

    @GetMapping("/manage/leader/edit_list")
    public String showUserTrainingToEdit(Model model) {
        model.addAttribute("acceptList", userTrainingService.getAcceptUserTrainingListForTeamLeader());
        model.addAttribute("deniedList", userTrainingService.getDeniedUserTrainingListForTeamLeader());
        return "/user_training/manage/leader/edit_list";
    }

//    endpoint for manager to manage userTraining

    @GetMapping("/manage/manager/list_to_accept")
    public String prepareTrainingToAcceptListForManager(@RequestParam(value = "departmentId", required = false) Long departmentId, Model model) {
        List<DepartmentDTO> departmentList = departmentService.getDepartmentList();
        model.addAttribute("departmentList", departmentList);
        if (departmentId != null) {
            model.addAttribute("trainingList", userTrainingService.getTrainingListToAcceptForManager(departmentId));
        } else {
            model.addAttribute("trainingList", userTrainingService.getTrainingListToAcceptForManager(departmentList.get(0).getId()));
        }
        return "/user_training/manage/manager/to_accept_list";
    }

    @GetMapping("/manage/manager/edit_list")
    public String prepareTrainingToEditListForManager(@RequestParam(value = "departmentId", required = false) Long departmentId, Model model) {
        List<DepartmentDTO> departmentList = departmentService.getDepartmentList();
        model.addAttribute("departmentList", departmentList);
        if (departmentId != null) {
            model.addAttribute("acceptList", userTrainingService.getAcceptUserTrainingListForManager(departmentId));
            model.addAttribute("deniedList", userTrainingService.getDeniedUserTrainingListForManager(departmentId));
        } else {
            model.addAttribute("acceptList", userTrainingService.getAcceptUserTrainingListForManager(departmentList.get(0).getId()));
            model.addAttribute("deniedList", userTrainingService.getDeniedUserTrainingListForManager(departmentList.get(0).getId()));
        }
        return "/user_training/manage/manager/edit_list";
    }

//    endpoint common for team leader and manager

    @GetMapping("/manage/details_to_add/{id}")
    public String prepareTrainingToManage(@PathVariable("id") Long id, Model model) {
        UserTrainingDetailsToManageDTO userTrainingDetailsToManageDTO = userTrainingService.getUserTrainingDetailsForManage(id);
        model.addAttribute("training", userTrainingDetailsToManageDTO);
        model.addAttribute("numberAcceptTraining", userTrainingService.getCountAcceptTraining(userTrainingDetailsToManageDTO.getTitle()));
        return "/user_training/manage/add_accept_or_denied";
    }

    @PostMapping("/manage/add_accept")
    public String acceptTraining(@ModelAttribute("userTrainingId") Long id) {
        userTrainingService.acceptTraining(id);
        if (SecurityUtils.getUserRole().equals("ROLE_TEAM_LEADER")) {
            return "redirect:/user_training/manage/leader/list_to_accept";
        } else {
            Long departmentId = userTrainingService.getDepartmentIdByUserTrainingId(id);
            return "redirect:/user_training/manage/manager/list_to_accept?departmentId=" + departmentId;
        }
    }

    @PostMapping("/manage/add_denied")
    public String deniedTraining(@ModelAttribute("userTrainingId") Long id) {
        userTrainingService.deniedTraining(id);
        if (SecurityUtils.getUserRole().equals("ROLE_TEAM_LEADER")) {
            return "redirect:/user_training/manage/leader/list_to_accept";
        } else {
            Long departmentId = userTrainingService.getDepartmentIdByUserTrainingId(id);
            return "redirect:/user_training/manage/manager/list_to_accept?departmentId=" + departmentId;
        }
    }

    @GetMapping("/manage/details_to_edit/{id}")
    public String prepareTrainingToManageEdit(@PathVariable("id") Long id, Model model) {
        UserTrainingDetailsToManageDTO userTrainingDetailsToManageDTO = userTrainingService.getUserTrainingDetailsForManage(id);
        model.addAttribute("training", userTrainingDetailsToManageDTO);
        model.addAttribute("numberAcceptTraining", userTrainingService.getCountAcceptTraining(userTrainingDetailsToManageDTO.getTitle()));
        return "/user_training/manage/edit_accept_or_denied";
    }

    @PostMapping("/manage/edit_accept")
    public String editAcceptTraining(@ModelAttribute("userTrainingId") Long id) {
        userTrainingService.acceptTraining(id);
        if (SecurityUtils.getUserRole().equals("ROLE_TEAM_LEADER")) {
            return "redirect:/user_training/manage/leader/edit_list";
        } else {
            Long departmentId = userTrainingService.getDepartmentIdByUserTrainingId(id);
            return "redirect:/user_training/manage/manager/edit_list?departmentId=" + departmentId;
        }
    }

    @PostMapping("/manage/edit_denied")
    public String editDeniedTraining(@ModelAttribute("userTrainingId") Long id) {
        userTrainingService.deniedTraining(id);
        if (SecurityUtils.getUserRole().equals("ROLE_TEAM_LEADER")) {
            return "redirect:/user_training/manage/leader/edit_list";
        } else {
            Long departmentId = userTrainingService.getDepartmentIdByUserTrainingId(id);
            return "redirect:/user_training/manage/manager/edit_list?departmentId=" + departmentId;
        }
    }


}
