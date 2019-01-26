package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.repository.ProductRepository;
import ge.idealab.kedi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
}
