package ge.idealab.kedi.model.converters;

import ge.idealab.kedi.model.enums.Condition;

import javax.persistence.AttributeConverter;

public class ConditionConverter implements AttributeConverter<Condition, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Condition attribute) {
        return attribute.getId();
    }

    @Override
    public Condition convertToEntityAttribute(Integer dbData) {
        return Condition.getFromId(dbData);
    }
}
