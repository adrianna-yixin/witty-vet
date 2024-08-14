package net.yixin.witty_vet.service;

import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.RegistrationRequest;

public interface UserService {
    User add(RegistrationRequest request);
}
