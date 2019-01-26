package ge.idealab.kedi.model.converters;

import ge.idealab.kedi.model.enums.Size;

import javax.persistence.AttributeConverter;

public class SizeConverter implements AttributeConverter<Size, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Size size) {
        return size.getId();
    }

    @Override
    public Size convertToEntityAttribute(Integer id) {
        return Size.getFromId(id);
    }
}
