package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.model.user.PersonalInformation;
import ge.idealab.kedi.dto.PersonalInformationDTO;
import ge.idealab.kedi.repository.PersonalInformationRepository;
import ge.idealab.kedi.service.PersonalInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {
    @Autowired
    private PersonalInformationRepository personalInformationRepository;
    @Override
    public PersonalInformationDTO create(PersonalInformationDTO personalInformationDTO) {
        ModelMapper modelMapper = new ModelMapper();
        PersonalInformation personalInformation = modelMapper.map(personalInformationDTO, PersonalInformation.class);
        personalInformation = personalInformationRepository.save(personalInformation);
        personalInformationDTO = modelMapper.map(personalInformation, PersonalInformationDTO.class);
        return personalInformationDTO;
    }
}
