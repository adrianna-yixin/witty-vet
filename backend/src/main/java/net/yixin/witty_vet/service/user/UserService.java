package net.yixin.witty_vet.service.user;

import net.yixin.witty_vet.dto.UserDto;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import net.yixin.witty_vet.request.UserUpdateRequest;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User registerUser(UserRegistrationRequest request);
    List<UserDto> getAllUsers();
    User findUserById(Long userId);
    UserDto getUserWithDetails(Long userId) throws SQLException;
    User updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);
}
