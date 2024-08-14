package net.yixin.witty_vet.service.user;

import net.yixin.witty_vet.dto.UserDto;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import net.yixin.witty_vet.request.UserUpdateRequest;

import java.util.List;

public interface UserService {
    User register(UserRegistrationRequest request);
    List<UserDto> getAllUsers();
    User findById(Long userId);
    User update(Long userId, UserUpdateRequest request);
    void delete(Long userId);
}
