package net.yixin.witty_vet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.yixin.witty_vet.dto.UserDto;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import net.yixin.witty_vet.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

@WebMvcTest(controllers = UserController.class)
public class UserTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setGender("Male");
        request.setPhoneNumber("1234567890");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");
        request.setUserType("PATIENT");
        request.setEnabled(false);
        request.setSpecialization("Cardiology");

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setGender("Male");
        user1.setPhoneNumber("1234567890");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        user1.setUserType("VET");
        user1.setEnabled(true);
        user1.setCreatedAt(LocalDate.now());
        user1.setSpecialization("Cardiology");
        user1.setAppointments(new ArrayList<>());
        user1.setReviews(new ArrayList<>());

        User user2 = new User();
        user2.setId(1L);
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setGender("Male");
        user2.setPhoneNumber("1234567890");
        user2.setEmail("john.doe@example.com");
        user2.setPassword("password");
        user2.setUserType("PATIENT");
        user2.setEnabled(true);
        user2.setCreatedAt(LocalDate.now());
        user2.setSpecialization("Cardiology");
        user2.setAppointments(new ArrayList<>());
    }

    @Test
    void registerShouldReturnCreatedWhenUserIsRegistered() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setGender("Male");
        request.setPhoneNumber("1234567890");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");
        request.setUserType("PATIENT");
        request.setEnabled(false);
        request.setSpecialization("Cardiology");

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setGender("Male");
        user1.setPhoneNumber("1234567890");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        user1.setUserType("VET");
        user1.setEnabled(true);
        user1.setCreatedAt(LocalDate.now());
        user1.setSpecialization("Cardiology");
        user1.setAppointments(new ArrayList<>());
        user1.setReviews(new ArrayList<>());
        UserDto registeredUser = modelMapper.map(user1, UserDto.class);
    }
}
