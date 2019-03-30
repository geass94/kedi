package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.BannerFileDTO;
import ge.idealab.kedi.model.banner.Banner;
import ge.idealab.kedi.model.banner.BannerFile;
import ge.idealab.kedi.repository.BannerFileRepository;
import ge.idealab.kedi.service.BannerFileService;
import ge.idealab.kedi.service.BannerFileService;
import ge.idealab.kedi.service.FileService;
import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BannerFileServiceImpl implements BannerFileService {
    @Autowired
    private BannerFileRepository bannerFileRepository;
    @Autowired
    private FileService fileService;

    @Override
    public BannerFile create(BannerFile bannerFile) {
        return bannerFileRepository.save(bannerFile);
    }

    @Override
    public BannerFile getOne(Long id) {
        return bannerFileRepository.getOne(id);
    }

    @Override
    public BannerFile getOneByName(String name) {
        return bannerFileRepository.findByName(name);
    }

    @Override
    public List<BannerFile> getBannerFilesByBanner(Banner pbanner) {
        return bannerFileRepository.findAllByBanner(pbanner);
    }

    @Override
    public Boolean delete(Long fid) throws IOException {
        BannerFile bannerFile = this.getOne(fid);
        Resource resource = fileService.loadFileAsResource(bannerFile.getName());
        File fileToDelete = FileUtils.getFile(resource.getFile().getAbsolutePath());
        boolean result = FileUtils.deleteQuietly(fileToDelete);
        if (result) {
            this.bannerFileRepository.delete(bannerFile);
        }
        return result;
    }

    @Override
    public List<BannerFile> addCaptions(List<BannerFileDTO> bannerFileDTOS) {
        List<BannerFile> bannerFiles = new ArrayList<>();
        for (BannerFileDTO c : bannerFileDTOS) {
            BannerFile o = bannerFileRepository.getOne(c.getId());
            o.setTitle(c.getTitle());
            o.setHeading(c.getHeading());
            o.setCaption(c.getCaption());
            o.setExternalURL(c.getExternalURL());
            o.setLabel(c.getLabel());
            bannerFiles.add(o);
        }
        bannerFiles = bannerFileRepository.saveAll(bannerFiles);
        return bannerFiles;
    }

}
