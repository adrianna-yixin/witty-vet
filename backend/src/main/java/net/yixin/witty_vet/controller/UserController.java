package net.yixin.witty_vet.controller;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.dto.UserDto;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.exception.AlreadyExistsException;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.UserRegistrationRequest;
import net.yixin.witty_vet.request.UserUpdateRequest;
import net.yixin.witty_vet.response.ApiResponse;
import net.yixin.witty_vet.service.user.UserService;
import net.yixin.witty_vet.utils.FeedbackMessage;
import net.yixin.witty_vet.utils.UrlMapping;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping(UrlMapping.REGISTER_USER)
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegistrationRequest request) {
        try {
            User user = userService.registerUser(request);
            UserDto registeredUser = modelMapper.map(user, UserDto.class);
            return new ResponseEntity<> (new ApiResponse(FeedbackMessage.CREATE_SUCCESS, registeredUser), HttpStatus.CREATED);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(UrlMapping.GET_ALL_USERS)
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_FOUND, users), HttpStatus.OK);
    }

    @GetMapping(UrlMapping.GET_USER_BY_ID)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long userId) {
        try {
            UserDto userDto = userService.getUserWithDetails(userId);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_FOUND, userDto), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(UrlMapping.UPDATE_USER)
    public ResponseEntity<ApiResponse> update(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        try {
            User user = userService.updateUser(userId, request);
            UserDto updatedUser = modelMapper.map(user, UserDto.class);
            return new ResponseEntity<> (new ApiResponse(FeedbackMessage.UPDATE_SUCCESS, updatedUser), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(UrlMapping.DELETE_USER_BY_ID)
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.DELETE_SUCCESS, null), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
