package net.yixin.witty_vet.service.user;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.yixin.witty_vet.dto.AppointmentDto;
import net.yixin.witty_vet.dto.ReviewDto;
import net.yixin.witty_vet.dto.UserDto;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.factory.UserFactory;
import net.yixin.witty_vet.model.Photo;
import net.yixin.witty_vet.model.Review;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import net.yixin.witty_vet.request.UserUpdateRequest;
import net.yixin.witty_vet.service.appointment.AppointmentService;
import net.yixin.witty_vet.service.photo.PhotoService;
import net.yixin.witty_vet.service.review.ReviewService;
import net.yixin.witty_vet.utils.FeedbackMessage;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;
    private final PhotoService photoService;
    private final ReviewService reviewService;

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
    public UserDto getUserWithDetails(Long userId) throws SQLException {
        User user = findUserById(userId); // Will throw exception
        UserDto userDto = modelMapper.map(user, UserDto.class);
        populateUserDtos(userDto, user);
        return userDto;
    }
    private void populateUserDtos(UserDto userDto, User user) throws SQLException {
        setUserPhoto(userDto, user);
        setUserAppointments(userDto);
        setUserReviews(userDto);
    }
    private void setUserPhoto(UserDto userDto, User user) throws SQLException {
        if (user.getPhoto() != null) {
            Long photoId = user.getPhoto().getId();
            userDto.setPhotoId(photoId);
            userDto.setPhoto(photoService.getImageData(photoId));
        }
    }
    private void setUserAppointments(UserDto userDto) {
        List<AppointmentDto> appointmentDtos = appointmentService.getUserAppointments(userDto.getId());
        userDto.setAppointments(appointmentDtos);
    }

    private void setUserReviews(UserDto userDto) {
        List<ReviewDto> reviewDtos = fetchAndMapReviews(userDto.getId());
        if (!reviewDtos.isEmpty()) {
            double averageRating = reviewService.getAverageRatingForVeterinarian(userDto.getId());
            userDto.setAverageRating(averageRating);
        }
        userDto.setReviews(reviewDtos);
    }

    @SneakyThrows
    private List<ReviewDto> fetchAndMapReviews(Long userId) {
        Page<Review> reviewPage = reviewService.findAllReviewsByPatientId(userId, 0, Integer.MAX_VALUE);
        return reviewPage.getContent().stream()
                .map(this::mapReviewToDto)
                .toList();
    }
    private ReviewDto mapReviewToDto(Review review) {
        try {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(review.getId());
            reviewDto.setStars(review.getStars());
            reviewDto.setFeedback(review.getFeedback());
            mapPatientInformation(review, reviewDto);
            mapVeterinarianInformation(review, reviewDto);
            return reviewDto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void mapPatientInformation(Review review, ReviewDto reviewDto) throws SQLException {
        if (review.getPatient() != null) {
            reviewDto.setPatientId(review.getPatient().getId());
            reviewDto.setPatientName(formatFullName(review.getPatient().getFirstName(), review.getPatient().getLastName()));
            setPatientPhoto(reviewDto, review.getPatient().getPhoto());
        }
    }
    private void mapVeterinarianInformation(Review review, ReviewDto reviewDto) throws SQLException {
        if (review.getVeterinarian() != null) {
            reviewDto.setVeterinarianId(review.getVeterinarian().getId());
            reviewDto.setVeterinarianName(formatFullName(review.getVeterinarian().getFirstName(), review.getVeterinarian().getLastName()));
            reviewDto.setVeterinarianSpecialization(review.getVeterinarian().getSpecialization());
            setVeterinarianPhoto(reviewDto, review.getVeterinarian().getPhoto());
        }
    }
    private String formatFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
    private void setVeterinarianPhoto(ReviewDto reviewDto, Photo photo) throws SQLException {
        reviewDto.setVeterinarianPhoto(photo != null ? photoService.getImageData(photo.getId()) : null);
    }
    private void setPatientPhoto(ReviewDto reviewDto, Photo photo) throws SQLException {
        reviewDto.setPatientPhoto(photo != null ? photoService.getImageData(photo.getId()) : null);
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
