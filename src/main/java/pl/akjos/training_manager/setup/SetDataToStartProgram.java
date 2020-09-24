package pl.akjos.training_manager.setup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SetDataToStartProgram implements ApplicationRunner {

    private final SetupDatabaseService setupDatabaseService;


    @Override
    public void run(ApplicationArguments args) {
        setupDatabaseService.setStartingData();
    }


}
