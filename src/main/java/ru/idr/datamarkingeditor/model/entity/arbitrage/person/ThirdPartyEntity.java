package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class ThirdPartyEntity extends PersonEntity {

    public ThirdPartyEntity(String value, IToken type) { super(value, type); }
    public ThirdPartyEntity(String value) { super(value, ArbitrageToken.ThirdParty); }

    @Override
    public String getValue() {
        
        return Entity.PATTERN_THIRD_PARTY_START + 
            this.innerRegexMap.getAll(CommonToken.Word, ArbitrageToken.Keyword) + 
            Entity.PATTERN_THIRD_PARTY_END;
    }
    
}
