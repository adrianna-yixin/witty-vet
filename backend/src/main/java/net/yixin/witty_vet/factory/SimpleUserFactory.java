package net.yixin.witty_vet.factory;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.exception.AlreadyExistsException;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleUserFactory implements UserFactory {
    private final UserRepository userRepository;
    private final VeterinarianFactory veterinarianFactory;
    private final PatientFactory patientFactory;
    private final AdminFactory adminFactory;

    @Override
    public User createUser(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByEmail(userRegistrationRequest.getEmail())) {
            throw new AlreadyExistsException("Oops! User with email " + userRegistrationRequest.getEmail() + " already exists!");
        }
        return switch (userRegistrationRequest.getUserType()) {
            case "VET" -> veterinarianFactory.createVeterinarian(userRegistrationRequest);
            case "PATIENT" -> patientFactory.createPatient(userRegistrationRequest);
            case "ADMIN" -> adminFactory.createAdmin(userRegistrationRequest);
            default -> null;
        };
    }
}
