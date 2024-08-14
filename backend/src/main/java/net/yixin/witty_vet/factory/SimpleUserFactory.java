package net.yixin.witty_vet.factory;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.exception.UserAlreadyExistsException;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleUserFactory implements UserFactory {
    private final UserRepository userRepository;
    private final VeterinarianFactory veterinarianFactory;
    private final PatientFactory patientFactory;
    private final AdminFactory adminFactory;

    @Override
    public User createUser(RegistrationRequest registrationRequest) {
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new UserAlreadyExistsException("Oops! User with email " + registrationRequest.getEmail() + " already exists!");
        }
        return switch (registrationRequest.getUserType()) {
            case "VET" -> veterinarianFactory.createVeterinarian(registrationRequest);
            case "PATIENT" -> patientFactory.createPatient(registrationRequest);
            case "ADMIN" -> adminFactory.createAdmin(registrationRequest);
            default -> null;
        };
    }
}
