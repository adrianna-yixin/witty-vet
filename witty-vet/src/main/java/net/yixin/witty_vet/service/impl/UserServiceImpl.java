package net.yixin.witty_vet.service.impl;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.factory.UserFactory;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.RegistrationRequest;
import net.yixin.witty_vet.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public User add(RegistrationRequest request) {
        return userFactory.createUser(request);
    }
}
