package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.BannerDTO;
import ge.idealab.kedi.exception.ResourceNotFoundException;
import ge.idealab.kedi.model.banner.Banner;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.repository.BannerRepository;
import ge.idealab.kedi.service.BannerService;
import ge.idealab.kedi.service.BannerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner create(BannerDTO bannerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Banner banner = modelMapper.map(bannerDTO, Banner.class);
        banner.setStatus(Status.ACTIVE);
        banner = bannerRepository.save(banner);
        return banner;
    }

    @Override
    public Banner getOne(Long id) {
        Banner c1 = bannerRepository.getOne(id);
        if (c1.equals(null))
            throw new ResourceNotFoundException("Banner", "Id", id);
        return bannerRepository.getOne(id);
    }

    @Override
    public Banner getOneByArea(String area) {
        return bannerRepository.findFirstByAreaAndStatus(area, Status.ACTIVE);
    }

    @Override
    public Banner switchStatus(BannerDTO bannerDTO, Long id) {
        Banner c = bannerRepository.getOne(id);
        c.setStatus(bannerDTO.getStatus() == Status.ACTIVE ? Status.DISABLED : Status.ACTIVE);
        c = bannerRepository.save(c);
        return c;
    }

    @Override
    public void delete(Long id) {
        Banner c = bannerRepository.getOne(id);
        c.setStatus(Status.DELETED);
        bannerRepository.save(c);
    }

    @Override
    public List<Banner> getAll() {
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.ACTIVE);
        statuses.add(Status.DISABLED);
        return bannerRepository.findAllByStatusIn(statuses);
    }
}
