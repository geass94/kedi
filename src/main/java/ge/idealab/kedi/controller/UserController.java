package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.PersonalInformationDTO;
import ge.idealab.kedi.dto.UserDTO;
import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.payload.response.UserResponse;
import ge.idealab.kedi.security.CurrentUser;
import ge.idealab.kedi.security.UserPrincipal;
import ge.idealab.kedi.service.PersonalInformationService;
import ge.idealab.kedi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PersonalInformationService personalInformationService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userService.getUserById(userPrincipal.getId()), UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/get-all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers(){
        List<UserDTO> userDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for(User user: userService.getAllUsers()){
            userDTOS.add(modelMapper.map(user, UserDTO.class));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @PostMapping("/personalinfo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getPersonalInformation(HttpServletRequest request, @RequestBody PersonalInformationDTO personalInformationDTO){
        return ResponseEntity.ok(personalInformationService.create(personalInformationDTO));
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){

        return null;
    }
}
