package net.yixin.witty_vet.service;

import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class UserAttributesMapper {
    public void setCommonAttributes(RegistrationRequest source, User target){
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setGender(source.getGender());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setUserType(source.getUserType());
        target.setEnabled(source.isEnabled());
    }
}
