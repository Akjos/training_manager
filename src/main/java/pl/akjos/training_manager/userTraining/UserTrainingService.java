package pl.akjos.training_manager.userTraining;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akjos.training_manager.domain.repositories.UserRepository;
import pl.akjos.training_manager.domain.repositories.UserTrainingRepository;

@Service
@RequiredArgsConstructor
public class UserTrainingService {
    private final UserTrainingRepository userTrainingRepository;
    private final UserRepository userRepository;
}
