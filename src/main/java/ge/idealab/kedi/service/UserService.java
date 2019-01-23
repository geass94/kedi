package ge.idealab.kedi.service;

import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.payload.request.SignUpRequest;

public interface UserService {
    User getUserById(Long id);
    User create(SignUpRequest signUpRequest);
    Boolean emailExists(String email);
}
