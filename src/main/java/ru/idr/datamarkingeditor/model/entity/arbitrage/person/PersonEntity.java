package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import java.util.Set;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public abstract class PersonEntity extends Entity {
    
    public PersonEntity(String value, IToken type) { 
        super(value, type); 
    }

    abstract public String getValue();

    @Override
    public Entity process() {
        Set<Entity> relatedKeyword =  this.related.getOfType(ArbitrageToken.Keyword);
        for (Entity keyword : relatedKeyword) {
            this.innerRegexMap.putAll(
                ArbitrageToken.Keyword, 
                keyword.getInnerRegexMap().values(ArbitrageToken.Keyword)
            );
        }
            
        Set<Entity> relatedWord =  this.related.getOfType(CommonToken.Word);
        for (Entity word : relatedWord) {
            this.innerRegexMap.putAll(
                CommonToken.Word, 
                word.getInnerRegexMap().values(CommonToken.Word)
            );
        }

        return this;
    }

    @Override
    public Entity merge(Entity otherEntity) {
        if (this.getClass() != otherEntity.getClass()) throw new IllegalArgumentException("Merging enitties must have the same type.");

        // Merge All Related Keywords Tokens
        this.innerRegexMap.putAll(
            ArbitrageToken.Keyword, 
            otherEntity.getInnerRegexMap().values(ArbitrageToken.Keyword)
        );

        // Merge All Related Words Tokens
        this.innerRegexMap.putAll(
            CommonToken.Word, 
            otherEntity.getInnerRegexMap().values(CommonToken.Word)    
        );

        this.related.addAll(otherEntity.getRelated());        

        return this;
    }

    @Override
    public Entity connect(Entity otherEntity) {
        super.connect(otherEntity);
        this.process();

        return this;
    }
}
