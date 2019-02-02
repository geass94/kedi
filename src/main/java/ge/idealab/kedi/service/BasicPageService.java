package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.BasicPageDTO;
import ge.idealab.kedi.model.BasicPage;

public interface BasicPageService {
    BasicPage create(BasicPageDTO basicPageDTO);
    BasicPage getByAlias(String alias);
}
