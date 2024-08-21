package net.yixin.witty_vet.service.user;

import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class UserAttributesMapper {
    public void setCommonAttributes(UserRegistrationRequest source, User target){
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setGender(source.getGender());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setEmail(source.getEmail());
        target.setUserType(source.getUserType());
        target.setEnabled(source.isEnabled());
    }
}
