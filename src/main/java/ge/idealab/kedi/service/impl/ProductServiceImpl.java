package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.CategoryDTO;
import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.dto.ProductFileDTO;
import ge.idealab.kedi.exception.ResourceNotFoundException;
import ge.idealab.kedi.model.Category;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.Color;
import ge.idealab.kedi.model.product.Manufacturer;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.repository.CategoryRepository;
import ge.idealab.kedi.repository.ColorRepository;
import ge.idealab.kedi.repository.ManufacturerRepository;
import ge.idealab.kedi.repository.ProductRepository;
import ge.idealab.kedi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product create(ProductDTO productDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productDTO, Product.class);

        if(product.getColor().getId() != null){
            Color color = colorRepository.getOne(product.getColor().getId());
            product.setColor(color);
        }

        if(product.getManufacturer().getId() != null){
            Manufacturer manufacturer = manufacturerRepository.getOne(product.getManufacturer().getId());
            product.setManufacturer(manufacturer);
        }

        List<Category> categories = new ArrayList<>();
        for(Category category: product.getCategoryList()){
            if(category.getId() != null){
                categories.add(categoryRepository.getOne(category.getId()));
            }else{
                categories.add(categoryRepository.save(category));
            }
        }
        product.setCategoryList(categories);

        if (product.getProductVariantId() != null && product.getProductVariantIds() != null && product.getProductVariantIds().length > 0){
            product.setBaseProduct(false);
            product = productRepository.save(product);
            product.setProductVariantId(product.getId());

            Long[] variants = Arrays.copyOf(product.getProductVariantIds(), product.getProductVariantIds().length + 1);
            variants[product.getProductVariantIds().length] = product.getProductVariantId();
            product.setProductVariantIds(variants);
            product = productRepository.save(product);
            updateProductVariants(product.getProductVariantIds());
        }else{
            product.setBaseProduct(true);
            product = productRepository.save(product);

            product.setProductVariantId(product.getId());
            product.setProductVariantIds(new Long[]{product.getProductVariantId()});
            product = productRepository.save(product);
        }
        return product;
    }

    @Override
    public Product getOne(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", id)
        );
    }

    @Override
    public Page<Product> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAllByBaseProductIsTrue(pageable);
    }

    @Override
    public List<Product> getProductVariants(Long[] variantIds) {
        return productRepository.findAllByProductVariantIdIn(variantIds);
    }

    @Override
    public List<Product> getProductsByFilter(Long[] catIds, Long[] colorIds, Long[] manuIds, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Category> categories = this.mapCategoryIdsToCategories(catIds);
        List<Color> colors = this.mapColorIdsToColors(colorIds);
        List<Manufacturer> manufacturers = this.mapManufacturerIdsToManufacturers(manuIds);
        return productRepository.findAllByCategoryListInAndColorInAndManufacturerInAndPriceBetween(categories, colors, manufacturers, minPrice, maxPrice);
    }

    private void updateProductVariants(Long[] ids){
        for(Long id: ids){
            Product product = productRepository.getOne(id);
            product.setProductVariantIds(ids);
            productRepository.save(product);
        }
    }

    private List<Category> mapCategoryIdsToCategories(Long[] ids){
        if (ids.length > 0) {
            return categoryRepository.findAllByIdIn(ids);
        } else {
            return categoryRepository.findAll();
        }
    }

    private List<Color> mapColorIdsToColors(Long[] ids){
        if (ids.length > 0) {
            return colorRepository.findAllByIdIn(ids);
        } else {
            return colorRepository.findAll();
        }
    }

    private List<Manufacturer> mapManufacturerIdsToManufacturers(Long[] ids){
        if (ids.length > 0) {
            return manufacturerRepository.findAllByIdIn(ids);
        } else {
            return manufacturerRepository.findAll();
        }
    }

}
