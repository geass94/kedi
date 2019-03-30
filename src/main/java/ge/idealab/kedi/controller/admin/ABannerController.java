package ge.idealab.kedi.controller.admin;

import ge.idealab.kedi.dto.BannerDTO;
import ge.idealab.kedi.dto.BannerFileDTO;
import ge.idealab.kedi.model.banner.Banner;
import ge.idealab.kedi.model.banner.BannerFile;
import ge.idealab.kedi.service.BannerFileService;
import ge.idealab.kedi.service.BannerService;
import ge.idealab.kedi.service.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/banner")
public class ABannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private FileService fileService;
    @Autowired
    private BannerFileService bannerFileService;

    @PostMapping("/add-banner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBanner(@RequestBody BannerDTO bannerDTO){
        return ResponseEntity.ok(this.mapBanner(bannerService.create(bannerDTO)));
    }

    @PutMapping("/switch-status/{cid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> switchStatus(@PathVariable Long cid, @RequestBody BannerDTO bannerDTO) {
        return ResponseEntity.ok(this.mapBanner(bannerService.switchStatus(bannerDTO, cid)));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
    }

    @PostMapping("/add-captions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCaptions(@RequestBody List<BannerFileDTO> bannerFileDTOS) {
        return ResponseEntity.ok(this.mapBannerFiles(bannerFileService.addCaptions(bannerFileDTOS)));
    }

    @PostMapping(path = "/add-banner-file", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProductFile(@Valid @RequestPart("banner-id") String bannerId, @Valid @RequestPart("files") MultipartFile[] multipartFiles){
        ModelMapper modelMapper = new ModelMapper();
        Banner banner = bannerService.getOne(Long.valueOf(bannerId));
        List<BannerFileDTO> bannerFileDTOS = new ArrayList<>();
        for(MultipartFile multipartFile: multipartFiles){
            BannerFile bannerFile = modelMapper.map(fileService.uploadFile(multipartFile), BannerFile.class);
            bannerFile.setBanner(banner);
            bannerFile = bannerFileService.create(bannerFile);
            bannerFileDTOS.add(modelMapper.map(bannerFile, BannerFileDTO.class));
        }
        return ResponseEntity.ok(bannerFileDTOS);
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
