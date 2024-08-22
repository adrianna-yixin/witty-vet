package net.yixin.witty_vet.factory;

import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.UserRegistrationRequest;

public interface UserFactory {
    User createUser(UserRegistrationRequest userRegistrationRequest);
}
