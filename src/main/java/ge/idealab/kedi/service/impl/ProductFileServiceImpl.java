package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
import ge.idealab.kedi.repository.ProductFileRepository;
import ge.idealab.kedi.service.ProductFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFileServiceImpl implements ProductFileService {
    @Autowired
    private ProductFileRepository productFileRepository;

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
}
