package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.model.user.PersonalInformation;
import ge.idealab.kedi.dto.PersonalInformationDTO;
import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.repository.PersonalInformationRepository;
import ge.idealab.kedi.repository.UserRepository;
import ge.idealab.kedi.service.PersonalInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {
    @Autowired
    private PersonalInformationRepository personalInformationRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public PersonalInformationDTO create(PersonalInformationDTO personalInformationDTO) {
        ModelMapper modelMapper = new ModelMapper();

        PersonalInformation original = personalInformationRepository.getOne(personalInformationDTO.getId());
        original.setAddress1(personalInformationDTO.getAddress1());
        original.setAddress1(personalInformationDTO.getAddress2());
        original.setFirstName(personalInformationDTO.getFirstName());
        original.setLastName(personalInformationDTO.getLastName());
        original.setCompany(personalInformationDTO.getCompany());
        original.setState(personalInformationDTO.getState());
        original.setCity(personalInformationDTO.getCity());
        original.setCountry(personalInformationDTO.getCountry());
        original.setPostCode(personalInformationDTO.getPostCode());
        PersonalInformationDTO updated = modelMapper.map(personalInformationRepository.save(original), PersonalInformationDTO.class);
        return updated;
    }
}
