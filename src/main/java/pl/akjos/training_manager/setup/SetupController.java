package pl.akjos.training_manager.setup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SetupController {

    private final SetupDatabaseService setupDatabaseService;


    @RequestMapping("/restart_db")
    public String restartDbToDefault() {
        log.debug("Restart database");
        setupDatabaseService.restoreDatabase();
        return "redirect:/";
    }
}
