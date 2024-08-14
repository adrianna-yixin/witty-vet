package net.yixin.witty_vet.factory;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.model.Veterinarian;
import net.yixin.witty_vet.repository.VeterinarianRepository;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import net.yixin.witty_vet.service.user.UserAttributesMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinarianFactory {
    private final VeterinarianRepository veterinarianRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Veterinarian createVeterinarian(UserRegistrationRequest request) {
        Veterinarian veterinarian = new Veterinarian();
        userAttributesMapper.setCommonAttributes(request, veterinarian);
        veterinarian.setSpecialization(request.getSpecialization());
        return veterinarianRepository.save(veterinarian);
    }
}
