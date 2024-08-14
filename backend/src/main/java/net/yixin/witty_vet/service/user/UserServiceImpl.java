package net.yixin.witty_vet.service.user;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.dto.EntityConverter;
import net.yixin.witty_vet.dto.UserDto;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.factory.UserFactory;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.RegistrationRequest;
import net.yixin.witty_vet.request.UserUpdateRequest;
import net.yixin.witty_vet.utils.FeedbackMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final EntityConverter<User, UserDto> entityConverter;

    @Override
    public User register(RegistrationRequest request) {
        return userFactory.createUser(request);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> entityConverter.mapEntityToDto(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.NOT_FOUND));
    }

    @Override
    public User update(Long userId, UserUpdateRequest request) {
        User user = findById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setSpecialization(request.getSpecialization());
        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND);
        });
    }
}
