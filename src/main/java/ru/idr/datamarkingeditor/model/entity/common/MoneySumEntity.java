package ru.idr.datamarkingeditor.model.entity.common;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class MoneySumEntity extends Entity {

    public MoneySumEntity(String value, IToken type) { super(value, type); }
    public MoneySumEntity(String value) { super(value, CommonToken.MoneySum); }

    @Override
    public String getValue() {
        return CommonToken.MoneySum.getRegex();
    }

    @Override
    public Entity process() {
        this.value = this.getValue();
        this.innerRegexMap.put(CommonToken.MoneySum, this.getValue());
        
        return this;
    }

    @Override
    public Entity merge(Entity otherEntity) {
        if (!(otherEntity instanceof MoneySumEntity)) throw new IllegalArgumentException("Merging enitties must have the same type.");
        if (!this.value.equals(otherEntity.getValue())) throw new IllegalArgumentException("MoneySums must have equal values to be merged");
        if (!this.innerRegexMap.equals(otherEntity.getInnerRegexMap())) throw new IllegalArgumentException("MoneySum musts have equal inner regex to be merged");
        
        this.related.addAll(otherEntity.getRelated());

        return this;
    }
    
}
