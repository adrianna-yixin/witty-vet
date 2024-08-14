package net.yixin.witty_vet.factory;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.model.Patient;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.PatientRepository;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.RegistrationRequest;
import net.yixin.witty_vet.service.UserAttributesMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientFactory {
    private final PatientRepository patientRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Patient createPatient(RegistrationRequest request) {
        Patient patient = new Patient();
        userAttributesMapper.setCommonAttributes(request, patient);
        return patientRepository.save(patient);
    }
}
