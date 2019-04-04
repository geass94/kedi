package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.BundleDTO;
import ge.idealab.kedi.dto.CategoryDTO;
import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.exception.ResourceNotFoundException;
import ge.idealab.kedi.model.product.ProductFile;
import ge.idealab.kedi.model.product.attribute.Category;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.attribute.Color;
import ge.idealab.kedi.model.product.attribute.Manufacturer;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.attribute.Size;
import ge.idealab.kedi.payload.response.PriceRange;
import ge.idealab.kedi.repository.*;
import ge.idealab.kedi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

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
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ProductFileRepository productFileRepository;

    @Override
    public Product create(ProductDTO productDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productDTO, Product.class);

        if (productDTO.getBaseVariantId() != null && productDTO.getBaseVariantId() > 0) {
            product.setBaseVariant(productRepository.getOne(productDTO.getBaseVariantId()));
        }

        if(product.getSize().getId() != null) {
            Size size = sizeRepository.getOne(product.getSize().getId());
            product.setSize(size);
        }

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

        if (product.getBaseVariant() != null){
            Product base = productRepository.getOne(product.getBaseVariant().getId());
            base.setTotalQuantity( base.getTotalQuantity() + product.getQuantity() );
            base = productRepository.save(base);

            product.setBaseProduct(false);
            product = productRepository.save(product);
            product.setBaseVariant(base);

            List<Product> variants = base.getVariants();
            if (!variants.contains(product)) {
                variants.add(product);
            }
            base.setVariants(variants);
            productRepository.save(base);
//            product.setVariants(variants);
            product = productRepository.save(product);
//            updateProductVariants(product.getProductVariantIds());
        }else{
            product.setBaseProduct(true);
            product.setTotalQuantity(product.getQuantity());
            product = productRepository.save(product);

            product.setBaseVariant(product);

            List<Product> variants = new ArrayList<>();
            variants.add(product);
            product.setVariants(variants);
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
    public Product getOneByParams(Long id, Long sizeId) {
        if (sizeId > 0) {
            Size size = sizeRepository.getOne(sizeId);
            return productRepository.findFirstByIdAndSizeAndStatus(id, size, Status.ACTIVE);
        } else {
            return productRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Product", "id", id)
            );
        }
    }

    @Override
    public Page<Product> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAllByBaseProductIsTrueAndMakeBundleIsFalse(pageable);
    }

    @Override
    public List<Product> getProductVariants(Product product) {
        return productRepository.findAllByBaseVariant(product);
    }

    @Override
    public Page<Product> getPaginatedProductsByFilter(List<Long> catIds, List<Long> colorIds, List<Long> manuIds, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        List<Category> categories = this.mapCategoryIdsToCategories(catIds);
        List<Color> colors = this.mapColorIdsToColors(colorIds);
        List<Manufacturer> manufacturers = this.mapManufacturerIdsToManufacturers(manuIds);
        if (maxPrice.doubleValue() == 0.0) {
            maxPrice = this.priceRange().getMaxPrice();
        }
        maxPrice = maxPrice.add(BigDecimal.ONE);
        minPrice = minPrice.subtract(BigDecimal.ONE);
        return productRepository.findDistinctByCategoryListInAndColorInAndManufacturerInAndPriceBetweenAndMakeBundleIsFalseAndStatus(pageable, categories, colors, manufacturers, minPrice, maxPrice, Status.ACTIVE);
    }

    @Override
    public Product update(ProductDTO p, Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Product p1 = productRepository.getOne(id);

        if (p.getQuantity() != null) {
            Long difference = p.getQuantity() - p1.getQuantity();
            p1.setTotalQuantity( p1.getTotalQuantity() + difference );
            p1.setQuantity(p.getQuantity());
        }

        if (p.getName() != null)
            p1.setName(p.getName());
        if (p.getPrice() != null)
            p1.setPrice(p.getPrice());
        if (p.getDescription() != null)
            p1.setDescription(p.getDescription());
        if (p.getCategoryList() != null) {
            List<Category> categories = new ArrayList<>();
            for (CategoryDTO c : p.getCategoryList()){
                categories.add(categoryRepository.getOne(c.getId()));
            }
            p1.setCategoryList(categories);
        }
        if (p.getSex() != null)
            p1.setSex(p.getSex());
        if (p.getSex() != null)
            p1.setSize(modelMapper.map(p.getSize(), Size.class));
        if (p.getColor() != null)
            p1.setColor(modelMapper.map(p.getColor(), Color.class));
        if (p.getManufacturer() != null)
            p1.setManufacturer(modelMapper.map(p.getManufacturer(), Manufacturer.class));
        if (p.getSize() != null)
            p1.setSize(modelMapper.map(p.getSize(), Size.class));
        p1.setUpdatedAt(new Date());
        p1 = productRepository.save(p1);
        return p1;
    }

    @Override
    public Product createBundle(BundleDTO bundleDTO) {
        Product bundle = this.create(bundleDTO.getProduct());
        List<Product> productsToBundle = new ArrayList<>();
        for (ProductDTO p : bundleDTO.getProducts()) {
            productsToBundle.add( productRepository.getOne(p.getId()) );
        }
        bundle.setBundledProducts(productsToBundle);
        bundle.setMakeBundle(true);
        bundle.setBaseProduct(false);
        bundle = productRepository.save(bundle);
        for (ProductFile pf : bundle.getBundledProducts().get(0).getProductFiles()) {
            ProductFile pf1 = new ProductFile();
            pf1.setProduct(bundle);
            pf1.setExtension(pf.getExtension());
            pf1.setFileSize(pf.getFileSize());
            pf1.setFileType(pf.getFileType());
            pf1.setStatus(Status.ACTIVE);
            pf1.setFileUrl(pf.getFileUrl());
            pf1.setName(pf.getName());
            pf1.setOriginalName(pf.getOriginalName());
            productFileRepository.save(pf1);
        }
        return bundle;
    }

    @Override
    public PriceRange priceRange() {
        PriceRange priceRange = new PriceRange();
        priceRange.setMinPrice(productRepository.getMinPrice());
        priceRange.setMaxPrice(productRepository.getMaxPrice());
        return priceRange;
    }

    @Override
    public List<Product> togglePromotion(List<ProductDTO> productDTOS) {
        List<Product> products = new ArrayList<>();
        for (ProductDTO p : productDTOS) {
            Product o = productRepository.getOne(p.getId());
            if (o.getPromoted() == null) {
                o.setPromoted(true);
            } else {
                o.setPromoted( !o.getPromoted() );
            }
            products.add(o);
        }
        products = productRepository.saveAll(products);
        return products;
    }

    @Override
    public List<Product> setSale(List<ProductDTO> productDTOS, Float sale, Date countDown) {
        List<Product> products = new ArrayList<>();
        for (ProductDTO p : productDTOS) {
            Product o = productRepository.getOne(p.getId());
            if (countDown != null) {
                o.setCountDown(countDown);
            }
            o.setSale( sale );
            products.add(o);
        }
        products = productRepository.saveAll(products);
        return products;
    }

    @Override
    public List<Product> refillStock(List<ProductDTO> productDTOS, Long quantity) {
        List<Product> products = new ArrayList<>();
        for (ProductDTO p : productDTOS) {
            Product o = productRepository.getOne(p.getId());
            o.setQuantity( quantity );
            products.add(o);
        }
        products = productRepository.saveAll(products);
        return products;
    }

    @Override
    public List<Product> getProductsForBundling() {
        return productRepository.findAllByMakeBundleIsFalseAndStatus(Status.ACTIVE);
    }

    @Override
    public List<Product> getProductsWithBundles(Long pid) {
        return productRepository.findAllByMakeBundleIsTrueAndBundledProductsContainsAndStatus(productRepository.getOne(pid), Status.ACTIVE);
    }

    @Override
    public List<Product> getFeaturedProducts() {
        return productRepository.findByPromotedIsTrueAndStatus(Status.ACTIVE);
    }

    @Override
    public List<Product> getBestSaleProducts() {
        return productRepository.findAllBySaleIsGreaterThanAndCountDownIsAfterAndMakeBundleIsFalseAndStatus(1f, new Date(), Status.ACTIVE);
    }

    @Override
    public List<Product> getProductsOnSale() {
        return productRepository.findSaleOff(0f, Status.ACTIVE);
    }

    @Override
    public List<Product> getRealtedProducts(Long productId) {
        Product product = productRepository.getOne(productId);
        return productRepository.findDistinctByCategoryListInAndIdIsNotAndMakeBundleIsFalseAndBaseProductIsTrueAndStatus(product.getCategoryList(), productId, Status.ACTIVE);
    }

    @Override
    public List<Product> getNewProducts() {
        Instant now = Instant.now(); //current date
        Instant before = now.minus(Duration.ofDays(30));
        Date dateBefore = Date.from(before);
        return productRepository.findAllByCreatedAtAfterAndMakeBundleIsFalseAndBaseProductIsTrueAndStatus(dateBefore, Status.ACTIVE);
    }

    private void updateProductVariants(List<Long> ids){
//        for(Long id: ids){
//            Product product = productRepository.getOne(id);
//            product.setProductVariantIds(ids);
//            productRepository.save(product);
//        }
    }

    private List<Category> mapCategoryIdsToCategories(List<Long> ids){
        if (ids.size() > 0) {
            return categoryRepository.findAllByIdIn(ids);
        } else {
            return categoryRepository.findAll();
        }
    }

    private List<Color> mapColorIdsToColors(List<Long> ids){
        if (ids.size() > 0) {
            return colorRepository.findAllByIdIn(ids);
        } else {
            return colorRepository.findAll();
        }
    }

    private List<Manufacturer> mapManufacturerIdsToManufacturers(List<Long> ids){
        if (ids.size() > 0) {
            return manufacturerRepository.findAllByIdIn(ids);
        } else {
            return manufacturerRepository.findAll();
        }
    }

}
