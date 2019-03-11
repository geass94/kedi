package ge.idealab.kedi.controller;

import ge.idealab.kedi.service.BasicPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basic-page")
@CrossOrigin(origins = "https://kedi.ge")
public class BasicPageController {
    @Autowired
    private BasicPageService basicPageService;
    @GetMapping("/{alias}")
    public ResponseEntity<?> getPageByAlias(@PathVariable String alias) {
        return ResponseEntity.ok(basicPageService.getByAlias(alias));
    }
}
