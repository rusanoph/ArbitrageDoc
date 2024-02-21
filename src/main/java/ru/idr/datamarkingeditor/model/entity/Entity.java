package ru.idr.datamarkingeditor.model.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.datamarkingeditor.model.InnerRegexMap;
import ru.idr.datamarkingeditor.model.entity.arbitrage.KeywordEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.CompetitionManagerEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.ComplainantEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.DefendantEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.FinancialManagerEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.ThirdPartyEntity;
import ru.idr.datamarkingeditor.model.entity.common.MoneySumEntity;
import ru.idr.datamarkingeditor.model.entity.common.WordEntity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;


public abstract class Entity {
    
    //#region Static Fields

    public static final List<IToken> ALLOWED_TOKENS = Stream.of(

        Arrays.asList(ArbitrageToken.values()),
        Arrays.asList(CommonToken.values())

    ).flatMap(Collection::stream)
    .collect(Collectors.toList());

    public static final List<IToken> SPECIAL_TOKEN = Stream.of(

        Arrays.asList(ArbitrageToken.values())

    ).flatMap(Collection::stream)
    .collect(Collectors.toList());

    //#region Pattern Start/End Constants
    //(?<Complainant>.+?)(?=( #regex# ))
    protected static final String PATTERN_COMPLAINANT_START = "(?<Complainant>.+?)(?=(";
    protected static final String PATTERN_COMPLAINANT_END = "))";

    //(?<Defendant>.+?)(?=( #regex# ))
    protected static final String PATTERN_DEFENDANT_START = "(?<Defendant>.+?)(?=(";
    protected static final String PATTERN_DEFENDANT_END = "))";

    //(?<ThirdParty>.+?)(?=( #regex# ))
    protected static final String PATTERN_THIRD_PARTY_START = "(?<ThirdParty>.+?)(?=(";
    protected static final String PATTERN_THIRD_PARTY_END = "))";

    //(?<CompetitionManager>.+?)(?=( #regex# ))
    protected static final String PATTERN_COMPETITION_MANAGER_START = "(?<CompetitionManager>.+?)(?=(";
    protected static final String PATTERN_COMPETITION_MANAGER_END = "))";

    //(?<FinancialManager>.+?)(?=( #regex# ))
    protected static final String PATTERN_FINANCIAL_MANAGER_START = "(?<FinancialManager>.+?)(?=(";
    protected static final String PATTERN_FINANCIAL_MANAGER_END = "))";

    //(?<Keyword> #regex# )
    protected static final String PATTERN_KEYWORD_START = "(?<Keyword>";
    protected static final String PATTERN_KEYWORD_END = ")";
    //#endregion

    //#endregion

    protected String value;
    protected IToken type;
    protected InnerRegexMap innerRegexMap;
    protected EntityMap related;

    public Entity(String value, IToken type) {
        this.value = preprocess(value);
        this.type = type;
        this.innerRegexMap = new InnerRegexMap();
        this.related = new EntityMap();

        this.process();
    }
    
    abstract public Entity process();
    abstract public Entity merge(Entity otherEntity);

    public Entity connect(Entity otherEnity) {
        this.related.add(otherEnity);
        return this;
    }

    //#region Getter/Setter
    public String getValue() { return value; }
    public IToken getType() { return type; }
    public InnerRegexMap getInnerRegexMap() { return innerRegexMap; }
    public EntityMap getRelated() { return related; }
    //#endregion

    //#region Property Checking
    public boolean ofType(IToken token) {
        return this.type == token;
    }

    public boolean bothOfType(Entity otherEntity, IToken token) {
        boolean isOfType = this.ofType(type);
        boolean hasSameType = otherEntity.ofType(this.type);

        return  isOfType && hasSameType;
    }
    //#endregion

    //#region Json Serialization
    public static Entity fromJsonObject(String rawJson) { 
        JSONObject json = new JSONObject(rawJson);
        return Entity.fromJsonObject(json);
    };
    
    public static Entity fromJsonObject(JSONObject json) {
        IToken type = IToken.fromString(json.getString("type"));
        Entity entity = Entity.createInctance(type);

        entity.value = json.getString("value");
        entity.innerRegexMap = InnerRegexMap.fromJsonObject(json.getJSONObject("innerRegex"));
        entity.related = EntityMap.fromJsonArray(json.getJSONArray("related"));

        return entity;
    }

    public String toJsonString() { 
        return this.toJsonObject().toString(); 
    }
    
    public JSONObject toJsonObject() { 
        JSONObject entityJson = new JSONObject();

        entityJson
            .put("value", getValue())
            .put("type", getType().getLabel())
            .put("innerRegex", getInnerRegexMap().toJsonObject())
            .put("related", new JSONArray());

        return entityJson; 
    }
    //#endregion
    
    //#region Object Overrides
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Entity other = (Entity) obj;

        return Objects.equals(this.type, other.type) && 
            (Objects.equals(this.value, other.value) || this.type.notCommon());
    }

    @Override
    public int hashCode() {
        if (this.type.notCommon()) 
            return Objects.hash(this.type);
        else 
            return Objects.hash(this.value, this.type);
    } 
    //#endregion

    //#region Helper methods

    private static  Entity createInctance(IToken token) {
        switch (token) {
            case CommonToken.Word: return new WordEntity("", token);
            case CommonToken.MoneySum: return new MoneySumEntity("", token);

            case ArbitrageToken.Complainant: return new ComplainantEntity("", token);
            case ArbitrageToken.Defendant: return new DefendantEntity("", token);
            case ArbitrageToken.ThirdParty: return new ThirdPartyEntity("", token);
            case ArbitrageToken.CompetitionManager: return new CompetitionManagerEntity("", token);
            case ArbitrageToken.FinancialManager: return new FinancialManagerEntity("", token);
            case ArbitrageToken.Keyword: return new KeywordEntity("", token);
            
            default: return null;
        }
    }

    //#region Value preprocessing
    private String preprocess(String value) {
        value = value
            .trim()
            .toLowerCase();

        value = escapeSpecialRegex(value);
        value = wrapIfShort(value);

        return "("+ value +")";
    }

    private String escapeSpecialRegex(String value) {
        value = value.toLowerCase();
        
        String escapeRegexChars = "(([\\^$.|?*+()\\[\\]{}]))";
        value = value.replaceAll(escapeRegexChars, "\\\\$1");
        
        return value;
    }

    private String wrapIfShort(String value) {
        // Words shorter or equal than 3 letters 
        return 0 < value.length() && value.length() <= 3 ? "(^|\\s*)" + value + "($|\\s+)" : value;
    }
    //#endregion

    //#endregion
}
