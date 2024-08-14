package net.yixin.witty_vet.factory;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.model.Veterinarian;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.repository.VeterinarianRepository;
import net.yixin.witty_vet.request.RegistrationRequest;
import net.yixin.witty_vet.service.UserAttributesMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinarianFactory {
    private final VeterinarianRepository veterinarianRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Veterinarian createVeterinarian(RegistrationRequest request) {
        Veterinarian veterinarian = new Veterinarian();
        userAttributesMapper.setCommonAttributes(request, veterinarian);
        veterinarian.setSpecialization(request.getSpecialization());
        return veterinarianRepository.save(veterinarian);
    }
}
