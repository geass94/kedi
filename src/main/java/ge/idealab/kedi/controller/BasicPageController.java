package ge.idealab.kedi.controller;

import ge.idealab.kedi.service.BasicPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic-page")
public class BasicPageController {
    @Autowired
    private BasicPageService basicPageService;
    @GetMapping("/{alias}")
    public ResponseEntity<?> getPageByAlias(@PathVariable String alias) {
        return ResponseEntity.ok(basicPageService.getByAlias(alias));
    }
}
