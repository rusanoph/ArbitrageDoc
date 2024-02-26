package ru.idr.datamarkingeditor.model.entity.utility;

import ru.idr.datamarkingeditor.model.InnerRegexMap;
import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.EntityMap;
import ru.idr.datamarkingeditor.model.token.UtilityToken;

public class InitialEntity extends Entity {

    public InitialEntity() { 
        this.type = UtilityToken.Initial;
        this.value = this.type.getLabel();
        this.innerRegexMap = new InnerRegexMap();
        this.related = new EntityMap();
    }

    @Override
    public Entity process() {
        // Initial entity shouldn't be processed
        throw new UnsupportedOperationException("Initial entity shouldn't be processed (Unimplemented method 'process').");
    }

    @Override
    public Entity merge(Entity otherEntity) {
        if (!(otherEntity instanceof InitialEntity)) throw new IllegalArgumentException("Merging enitties must have the same type.");
        if (!this.value.equals(otherEntity.getValue())) throw new IllegalArgumentException("'Initial' must have equal values to be merged");
        
        this.related.addAll(otherEntity.getRelated());
        
        return this;
    }
    
}
