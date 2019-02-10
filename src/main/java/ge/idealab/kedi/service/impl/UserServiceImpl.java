package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.UserDTO;
import ge.idealab.kedi.exception.ResourceNotFoundException;
import ge.idealab.kedi.model.enums.AuthProvider;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.user.Address;
import ge.idealab.kedi.model.user.Authority;
import ge.idealab.kedi.model.user.PersonalInformation;
import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.payload.request.SignUpRequest;
import ge.idealab.kedi.repository.AuthorityRepository;
import ge.idealab.kedi.repository.UserRepository;
import ge.idealab.kedi.security.UserPrincipal;
import ge.idealab.kedi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User create(SignUpRequest signUpRequest) {
        User user = new User();
        List<Authority> authorities = authorityRepository.findAllByIdIn(Collections.singletonList(2L)); // Default ROLE_USER
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setStatus(Status.ACTIVE);
        user.setPersonalInformation(personalInformation);
        user.setName(signUpRequest.getName());
        user.setAuthorities(authorities);
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        userRepository.flush();
        return userRepository.save(user);
    }

    @Override
    public Boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User update(Long id, UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User user = userRepository.getOne(id);
        user.setPersonalInformation( modelMapper.map(userDTO.getPersonalInformation(), PersonalInformation.class) );
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User getUserFromContext() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userPrincipal.getId());
        return user;
    }
}
