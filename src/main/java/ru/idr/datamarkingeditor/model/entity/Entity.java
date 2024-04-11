package ru.idr.datamarkingeditor.model.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import ru.idr.datamarkingeditor.model.entity.utility.InitialEntity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;
import ru.idr.datamarkingeditor.model.token.UtilityToken;


public abstract class Entity {
    
    //#region Static Fields

    public static final List<IToken> ALLOWED_TOKENS = Stream.of(

        // Arrays.asList(Service),
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
    protected String rawValue;
    protected IToken type;
    protected InnerRegexMap innerRegexMap;
    protected EntityMap related;
    protected Boolean visited = false;

    protected Entity() {}
    protected Entity(String value, IToken type) {
        this.rawValue = replaceSpaces(value);
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
        this.visited = false;
        return this;
    }

    //#region Getter/Setter
    public String getRawValue() { return rawValue; }  
    public String getValue() { return value; }  // This method can be overrided in inheritors
    public IToken getType() { return type; }
    public InnerRegexMap getInnerRegexMap() { return innerRegexMap; }
    public EntityMap getRelated() { return related; }
    public Boolean isVisited() { return visited; }
    
    public void setVisit(Boolean visited) { this.visited = visited; } 
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
        Entity entity = Entity.createInstance(type);

        entity.value = json.getString("value");
        entity.innerRegexMap = InnerRegexMap.fromJsonObject(json.getJSONObject("innerRegex"));
        entity.related = new EntityMap();

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
            .put("related", getRelated().toJsonArrayAsHashCodes());

        return entityJson; 
    }
    //#endregion
    
    //#region Object Overrides
    @Override
    public String toString() {
        
        // toString this object
        String result = String.format("%s %s -> %d (%s)\r\n", this.getType(), this.getValue(), this.hashCode(), this.link());

        // toString related/child objects
        for (Entity relatedEntity : this.related) {
            result += String.format("\t%s %s -> %d (%s)\r\n", relatedEntity.getType(), relatedEntity.getValue(), relatedEntity.hashCode(), relatedEntity.link());
        }
        
        return result;
    }

    private String link() { return super.toString(); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Entity other = (Entity) obj;

        // If entity is common, then equals by type and value, else only by type
        return Objects.equals(this.type, other.type) && 
            (Objects.equals(this.value, other.value) || this.type.notCommon());
    }

    @Override
    public int hashCode() {
        if (this.type.notCommon()) 
            return Objects.hash(this.type.getLabel());
        else 
            return Objects.hash(this.value, this.type.getLabel());
    } 
    //#endregion

    //#region Helper methods
    public static  Entity createInstance(IToken token) {
        return Entity.createInstance("", token);
    }

    public static  Entity createInstance(String value, IToken token) {
        switch (token) {
            case UtilityToken.Initial: return new InitialEntity();

            case CommonToken.Word: return new WordEntity(value, token);
            case CommonToken.MoneySum: return new MoneySumEntity(value, token);

            case ArbitrageToken.Complainant: return new ComplainantEntity(value, token);
            case ArbitrageToken.Defendant: return new DefendantEntity(value, token);
            case ArbitrageToken.ThirdParty: return new ThirdPartyEntity(value, token);
            case ArbitrageToken.CompetitionManager: return new CompetitionManagerEntity(value, token);
            case ArbitrageToken.FinancialManager: return new FinancialManagerEntity(value, token);
            case ArbitrageToken.Keyword: return new KeywordEntity(value, token);
            
            default: return null;
        }
    }

    //#region Value preprocessing
    private String preprocess(String value) {
        value = replaceSpaces(value)
            .trim()
            .toLowerCase();

        value = this.escapeSpecialRegex(value);
        // value = this.wrapIfShort(value);
        value = this.wrapAsWord(value);

        return "("+ value +")";
    }

    private String wrapAsWord(String value) {
        return "(^|\\s+)" + value + "($|\\s+)";
    }

    private String replaceSpaces(String value) {
        return value.replaceAll("[\\s\\r\\n]+", " ");
    }

    private String escapeSpecialRegex(String value) {
        value = value.toLowerCase();
        
        String escapeRegexChars = "(([\\^$.|?*+()\\[\\]{}]))";
        value = value.replaceAll(escapeRegexChars, "\\\\$1");
        
        return value;
    }

    private String wrapIfShort(String value) {
        // Words shorter or equal than 3 letters 
        return 0 < value.length() && value.length() <= 3 ? "(^|\\s+)" + value + "($|\\s+)" : value;
    }
    //#endregion

    //#endregion
}
