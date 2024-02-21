package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class DefendantEntity extends PersonEntity {

    public DefendantEntity(String value, IToken type) { super(value, type); }
    public DefendantEntity(String value) { super(value, ArbitrageToken.Defendant); }

    @Override
    public String getValue() {

        return Entity.PATTERN_DEFENDANT_START + 
            this.innerRegexMap.getAll(CommonToken.Word, ArbitrageToken.Keyword) + 
            Entity.PATTERN_DEFENDANT_END;
    }
    
}
