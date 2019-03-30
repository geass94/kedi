package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.BannerDTO;
import ge.idealab.kedi.model.banner.Banner;

import java.util.List;

public interface BannerService {
    Banner create(BannerDTO bannerDTO);
    Banner getOne(Long id);
    Banner getOneByArea(String area);
    Banner switchStatus(BannerDTO bannerDTO, Long id);
    void delete(Long id);
    List<Banner> getAll();
}
