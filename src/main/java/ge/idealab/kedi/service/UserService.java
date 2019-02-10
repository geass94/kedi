package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.UserDTO;
import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.payload.request.SignUpRequest;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User create(SignUpRequest signUpRequest);
    Boolean emailExists(String email);
    List<User> getAllUsers();
    User update(Long id, UserDTO userDTO);

    User getUserFromContext();
}
