package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class FinancialManagerEntity extends PersonEntity {

    public FinancialManagerEntity(String value, IToken type) { super(value, type); }
    public FinancialManagerEntity(String value) { super(value, ArbitrageToken.FinancialManager); }

    @Override
    public String getValue() {

        return Entity.PATTERN_FINANCIAL_MANAGER_START + 
            this.innerRegexMap.getAll(CommonToken.Word, ArbitrageToken.Keyword) + 
            Entity.PATTERN_FINANCIAL_MANAGER_END;
    }
    
}
