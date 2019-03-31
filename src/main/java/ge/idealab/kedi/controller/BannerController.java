package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.BannerDTO;
import ge.idealab.kedi.dto.BannerFileDTO;
import ge.idealab.kedi.model.banner.Banner;
import ge.idealab.kedi.model.banner.BannerFile;
import ge.idealab.kedi.service.BannerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllBanners() {
        return ResponseEntity.ok(this.mapBanners(bannerService.getAll()));
    }

    @GetMapping("/get-by-area/{area}")
    public ResponseEntity<?> getBannerByArea(@PathVariable String area) {
        return ResponseEntity.ok(this.mapBanner(bannerService.getOneByArea(area)));
    }

    @GetMapping("/get-all-by-area/{area}")
    public ResponseEntity<?> getBannersByArea(@PathVariable String area) {
        return ResponseEntity.ok(this.mapBanners(bannerService.getAllByArea(area)));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getBannerById(@PathVariable Long id) {
        return ResponseEntity.ok(this.mapBanner(bannerService.getOne(id)));
    }

    private List<BannerDTO> mapBanners(List<Banner> banners) {
        ModelMapper modelMapper = new ModelMapper();
        List<BannerDTO> bannerDTOS = new ArrayList<>();
        for (Banner c : banners) {
            BannerDTO x = modelMapper.map(c, BannerDTO.class);
            x.setBannerFiles(this.mapBannerFiles(c.getBannerFiles()));
            bannerDTOS.add(x);
        }
        return bannerDTOS;
    }

    private BannerDTO mapBanner(Banner banner) {
        ModelMapper modelMapper = new ModelMapper();
        BannerDTO c = modelMapper.map(banner, BannerDTO.class);
        c.setBannerFiles(this.mapBannerFiles(banner.getBannerFiles()));
        return c;
    }

    private List<BannerFileDTO> mapBannerFiles(List<BannerFile> bannerFiles) {
        ModelMapper modelMapper = new ModelMapper();
        List<BannerFileDTO> bannerFileDTOS = new ArrayList<>();
        for (BannerFile c : bannerFiles) {
            bannerFileDTOS.add(modelMapper.map(c, BannerFileDTO.class));
        }
        return bannerFileDTOS;
    }
}
