package net.yixin.witty_vet.factory;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.model.Admin;
import net.yixin.witty_vet.repository.AdminRepository;
import net.yixin.witty_vet.request.RegistrationRequest;
import net.yixin.witty_vet.service.user.UserAttributesMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFactory {
    private final AdminRepository adminRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Admin createAdmin(RegistrationRequest request) {
        Admin admin = new Admin();
        userAttributesMapper.setCommonAttributes(request, admin);
        return adminRepository.save(admin);
    }
}
