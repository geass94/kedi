package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
import ge.idealab.kedi.repository.ProductFileRepository;
import ge.idealab.kedi.service.FileService;
import ge.idealab.kedi.service.ProductFileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductFileServiceImpl implements ProductFileService {
    @Autowired
    private ProductFileRepository productFileRepository;
    @Autowired
    private FileService fileService;

    @Override
    public ProductFile create(ProductFile productFile) {
        return productFileRepository.save(productFile);
    }

    @Override
    public ProductFile getOne(Long id) {
        return productFileRepository.getOne(id);
    }

    @Override
    public ProductFile getOneByName(String name) {
        return productFileRepository.findByName(name);
    }

    @Override
    public List<ProductFile> getProductFilesByProduct(Product product) {
        return productFileRepository.findAllByProduct(product);
    }

    @Override
    public Boolean delete(Long fid) throws IOException {
        ProductFile productFile = this.getOne(fid);
        Resource resource = fileService.loadFileAsResource(productFile.getName());
        File fileToDelete = FileUtils.getFile(resource.getFile().getAbsolutePath());
        boolean result = FileUtils.deleteQuietly(fileToDelete);
        if (result) {
            this.productFileRepository.delete(productFile);
        }
        return result;
    }
}
