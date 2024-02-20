package ru.idr.datamarkingeditor.model.entity;

import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class WordEntity extends Entity {
    
    public WordEntity(String value, IToken type) { super(value, type); }
    public WordEntity(String value) { super(value, CommonToken.Word); }
    
    @Override
    public Entity merge(Entity otherEntity) {
        if (!(otherEntity instanceof WordEntity)) throw new IllegalArgumentException("Merging enitties must have the same type.");
        if (!this.value.equals(otherEntity.value)) throw new IllegalArgumentException("Words must have equal values to be merged");
        if (!this.innerRegexMap.equals(otherEntity.innerRegexMap)) throw new IllegalArgumentException("Words must have equal inner regex to be merged");
        
        this.related.addAll(otherEntity.related);

        return this;
    }

    @Override
    public Entity process() {
        this.innerRegexMap.put(CommonToken.Word, this.value);
        return this;
    }
}
