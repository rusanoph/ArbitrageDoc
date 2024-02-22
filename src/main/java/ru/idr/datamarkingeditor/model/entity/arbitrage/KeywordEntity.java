package ru.idr.datamarkingeditor.model.entity.arbitrage;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class KeywordEntity extends Entity {
    
    public KeywordEntity(String value, IToken type) { super(value, ArbitrageToken.Keyword); }
    public KeywordEntity(String value) { super(value, ArbitrageToken.Keyword); }

    @Override
    public String getValue() {
        if (this.innerRegexMap.isEmpty(ArbitrageToken.Keyword)) return this.value;

        return Entity.PATTERN_KEYWORD_START + 
            this.innerRegexMap.get(ArbitrageToken.Keyword) + 
            Entity.PATTERN_KEYWORD_END;
    }

    @Override
    public Entity process() {
        this.innerRegexMap.put(ArbitrageToken.Keyword, this.value);
        return this;
    }

    @Override
    public Entity merge(Entity otherEntity) {
        if (!(otherEntity instanceof KeywordEntity)) throw new IllegalArgumentException("Merging enitties must have the same type.");

        this.innerRegexMap.putAll(
            ArbitrageToken.Keyword, 
            otherEntity.getInnerRegexMap().values(ArbitrageToken.Keyword)
        );
        this.related.addAll(otherEntity.getRelated());

        return this;
    }

}
