package ge.idealab.kedi.controller.admin;

import ge.idealab.kedi.dto.BasicPageDTO;
import ge.idealab.kedi.model.BasicPage;
import ge.idealab.kedi.service.BasicPageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/page")
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

    private BasicPageDTO mapBasicPage(BasicPage basicPage) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(basicPage, BasicPageDTO.class);
    }
}
