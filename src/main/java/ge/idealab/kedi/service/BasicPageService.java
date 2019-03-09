package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.BasicPageDTO;
import ge.idealab.kedi.model.BasicPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BasicPageService {
    BasicPage create(BasicPageDTO basicPageDTO);
    BasicPage getByAlias(String alias);
    BasicPage update(BasicPageDTO basicPageDTO, Long id);
    Page<BasicPage> getAll(Pageable pageable);
}
