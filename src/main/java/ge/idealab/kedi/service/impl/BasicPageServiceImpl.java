package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.BasicPageDTO;
import ge.idealab.kedi.model.BasicPage;
import ge.idealab.kedi.repository.BasicPageRepository;
import ge.idealab.kedi.service.BasicPageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicPageServiceImpl implements BasicPageService {
    @Autowired
    private BasicPageRepository basicPageRepository;

    @Override
    public BasicPage create(BasicPageDTO basicPageDTO) {
        ModelMapper modelMapper = new ModelMapper();
        if(basicPageDTO.getAlias() == null){
            basicPageDTO.setAlias( basicPageDTO.getName().toLowerCase().replace(' ', '-') );
        }
        BasicPage basicPage = modelMapper.map(basicPageDTO, BasicPage.class);
        return basicPageRepository.save(basicPage);
    }

    @Override
    public BasicPage getByAlias(String alias) {
        return basicPageRepository.findByAlias(alias);
    }

    @Override
    public BasicPage update(BasicPageDTO basicPageDTO, Long id) {
        BasicPage o = basicPageRepository.getOne(id);
        o.setName(basicPageDTO.getName());
        o.setBody(basicPageDTO.getBody());
        o = basicPageRepository.save(o);
        return o;
    }
}
