package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.BannerFileDTO;
import ge.idealab.kedi.model.banner.Banner;
import ge.idealab.kedi.model.banner.BannerFile;

import java.io.IOException;
import java.util.List;

public interface BannerFileService {
    BannerFile create(BannerFile productFile);
    BannerFile getOne(Long id);
    BannerFile getOneByName(String name);
    List<BannerFile> getBannerFilesByBanner(Banner banner);
    Boolean delete(Long file) throws IOException;

    List<BannerFile> addCaptions(List<BannerFileDTO> bannerFileDTOS);
}
