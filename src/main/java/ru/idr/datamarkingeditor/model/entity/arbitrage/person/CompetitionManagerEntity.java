package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class CompetitionManagerEntity extends PersonEntity {
    
    public CompetitionManagerEntity(String value, IToken type) { super(value, type); }
    public CompetitionManagerEntity(String value) { super(value, ArbitrageToken.CompetitionManager); }

    @Override
    public String getValue() {
        
        return Entity.PATTERN_COMPETITION_MANAGER_START +
            this.innerRegexMap.getAll(CommonToken.Word, ArbitrageToken.Keyword) + 
            Entity.PATTERN_COMPETITION_MANAGER_END;
    }
}
