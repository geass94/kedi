package ge.idealab.kedi.model.product.attribute;

import ge.idealab.kedi.model.BaseStatusAuditEntity;
import ge.idealab.kedi.model.converters.ProductAttributeTypeConverter;
import ge.idealab.kedi.model.enums.ProductAttributeType;

import javax.persistence.Convert;
import java.util.List;

public class ProductAttributeGroup extends BaseStatusAuditEntity {
    private String name;
    @Convert(converter = ProductAttributeTypeConverter.class)
    private ProductAttributeType productAttributeType;
    private List<ProductAttribute> productAttributes;
}
