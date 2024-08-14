package net.yixin.witty_vet.service;

import net.yixin.witty_vet.dto.UserDto;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.RegistrationRequest;
import net.yixin.witty_vet.request.UserUpdateRequest;

import java.util.List;

public interface UserService {
    User register(RegistrationRequest request);
    List<UserDto> getAllUsers();
    User findById(Long userId);
    User update(Long userId, UserUpdateRequest request);
    void delete(Long userId);
}
