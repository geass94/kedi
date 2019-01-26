package ge.idealab.kedi.model.converters;

import ge.idealab.kedi.model.enums.Sex;

import javax.persistence.AttributeConverter;

public class SexConverter implements AttributeConverter<Sex, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Sex sex) {
        return sex.getId();
    }

    @Override
    public Sex convertToEntityAttribute(Integer id) {
        return Sex.getFromId(id);
    }
}
