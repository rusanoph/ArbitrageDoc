package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class ComplainantEntity extends PersonEntity {

    public ComplainantEntity(String value, IToken type) { super(value, type); }
    public ComplainantEntity(String value) { super(value, ArbitrageToken.Complainant); }

    @Override
    public String getValue() {
        
        return Entity.PATTERN_COMPLAINANT_START +
            this.innerRegexMap.getAll(CommonToken.Word, ArbitrageToken.Keyword) + 
            Entity.PATTERN_COMPLAINANT_END;
    }
    
}
