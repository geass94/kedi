package ge.idealab.kedi.service;

import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;

import java.io.IOException;
import java.util.List;

public interface ProductFileService {
    ProductFile create(ProductFile productFile);
    ProductFile getOne(Long id);
    ProductFile getOneByName(String name);
    List<ProductFile> getProductFilesByProduct(Product product);
    Boolean delete(Long file) throws IOException;
}
