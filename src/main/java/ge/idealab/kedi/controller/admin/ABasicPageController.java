package ge.idealab.kedi.controller.admin;

import ge.idealab.kedi.dto.BasicPageDTO;
import ge.idealab.kedi.model.BasicPage;
import ge.idealab.kedi.service.BasicPageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/page")
@CrossOrigin(origins = "https://admin.kedi.ge")
public class ABasicPageController {
    @Autowired
    private BasicPageService basicPageService;

    @PostMapping("/add-page")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBasicPage(@RequestBody BasicPageDTO basicPageDTO) {
        BasicPage basicPage = basicPageService.create(basicPageDTO);
        return ResponseEntity.ok(this.mapBasicPage(basicPage));
    }

    @PutMapping("/save-page/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> savePage(@PathVariable Long id, @RequestBody BasicPageDTO basicPageDTO) {
        BasicPage basicPage = basicPageService.update(basicPageDTO, id);
        return ResponseEntity.ok(this.mapBasicPage(basicPage));
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllPages(@RequestParam("sort") String sort,
                                         @RequestParam("order") String order,
                                         @RequestParam("page") String page) {
        Pageable sorting = PageRequest.of(Integer.valueOf(page), 30, order == "desc" ? Sort.by(sort).descending() : Sort.by(sort).ascending());
        Page<BasicPage> basicPagePage = basicPageService.getAll(sorting);
        PageImpl<?> pageImpl = new PageImpl<>(this.mapBasicPages(basicPagePage.getContent()), sorting, basicPagePage.getTotalElements());
        return ResponseEntity.ok(pageImpl);
    }

    private BasicPageDTO mapBasicPage(BasicPage basicPage) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(basicPage, BasicPageDTO.class);
    }

    private List<BasicPageDTO> mapBasicPages(List<BasicPage> basicPages) {
        ModelMapper modelMapper = new ModelMapper();
        List<BasicPageDTO> basicPageDTOS = new ArrayList<>();
        for (BasicPage bp : basicPages) {
            basicPageDTOS.add(modelMapper.map(bp, BasicPageDTO.class));
        }
        return basicPageDTOS;
    }
}
