package net.yixin.witty_vet.service.user;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.dto.UserDto;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.factory.UserFactory;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import net.yixin.witty_vet.request.UserUpdateRequest;
import net.yixin.witty_vet.utils.FeedbackMessage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final ModelMapper modelMapper;

    @Override
    public User registerUser(UserRegistrationRequest request) {
        return userFactory.createUser(request);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND));
    }

    @Override
    public User updateUser(Long userId, UserUpdateRequest request) {
        User user = findUserById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setSpecialization(request.getSpecialization());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND));
        userRepository.deleteById(userId);
    }
}
