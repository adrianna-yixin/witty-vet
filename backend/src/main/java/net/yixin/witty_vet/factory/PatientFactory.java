package net.yixin.witty_vet.factory;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.model.Patient;
import net.yixin.witty_vet.repository.PatientRepository;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import net.yixin.witty_vet.service.user.UserAttributesMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientFactory {
    private final PatientRepository patientRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Patient createPatient(UserRegistrationRequest request) {
        Patient patient = new Patient();
        userAttributesMapper.setCommonAttributes(request, patient);
        return patientRepository.save(patient);
    }
}
