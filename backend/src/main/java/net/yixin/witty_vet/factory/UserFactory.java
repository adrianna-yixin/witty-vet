package net.yixin.witty_vet.factory;

import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.RegistrationRequest;

public interface UserFactory {
    public User createUser(RegistrationRequest registrationRequest);
}
