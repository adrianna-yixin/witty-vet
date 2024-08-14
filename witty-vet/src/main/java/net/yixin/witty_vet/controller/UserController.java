package net.yixin.witty_vet.controller;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.request.RegistrationRequest;
import net.yixin.witty_vet.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User addUser(@RequestBody RegistrationRequest request) {
        return userService.add(request);
    }
}
