package ge.idealab.kedi.model.converters;

import ge.idealab.kedi.model.enums.ProductAttributeType;

import javax.persistence.AttributeConverter;

public class ProductAttributeTypeConverter implements AttributeConverter<ProductAttributeType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProductAttributeType attribute) {
        return attribute.getId();
    }

    @Override
    public ProductAttributeType convertToEntityAttribute(Integer dbData) {
        return ProductAttributeType.getFromId(dbData);
    }
}
