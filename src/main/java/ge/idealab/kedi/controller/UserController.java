package ge.idealab.kedi.controller;

import ge.idealab.kedi.payload.response.UserResponse;
import ge.idealab.kedi.security.CurrentUser;
import ge.idealab.kedi.security.UserPrincipal;
import ge.idealab.kedi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        ModelMapper modelMapper = new ModelMapper();
        UserResponse userResponse = modelMapper.map(userService.getUserById(userPrincipal.getId()), UserResponse.class);
        return userResponse;
    }
}
