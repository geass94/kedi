package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.BasicPageDTO;
import ge.idealab.kedi.exception.ResourceNotFoundException;
import ge.idealab.kedi.model.BasicPage;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.repository.BasicPageRepository;
import ge.idealab.kedi.service.BasicPageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BasicPageServiceImpl implements BasicPageService {
    @Autowired
    private BasicPageRepository basicPageRepository;

    @Override
    public Boolean checkAlias(String alias) {
        Integer count = basicPageRepository.checkAlias(alias, Status.ACTIVE);
        return count == 0;
    }

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
        BasicPage basicPage = basicPageRepository.findByAlias(alias);
        if (basicPage == null) {
            throw new ResourceNotFoundException("Page", "alias",  alias);
        }
        return basicPage;
    }

    @Override
    public BasicPage update(BasicPageDTO basicPageDTO, Long id) {
        BasicPage o = basicPageRepository.getOne(id);
        o.setName(basicPageDTO.getName());
        o.setBody(basicPageDTO.getBody());
        o = basicPageRepository.save(o);
        return o;
    }

    @Override
    public Page<BasicPage> getAll(Pageable pageable) {
        return basicPageRepository.findAllByStatus(pageable, Status.ACTIVE);
    }

    @Override
    public void delete(Long id) {
        BasicPage basicPage = basicPageRepository.getOne(id);
        basicPage.setStatus(Status.DELETED);
        basicPageRepository.save(basicPage);
    }
}
