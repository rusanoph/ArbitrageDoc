package ru.idr.datamarkingeditor.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.model.arbitrage.ArbitrageToken;

public class Entity {

    //#region Static Fields
    public static final Set<String> SPECIAL_TOKEN_TYPE = 
        Set.of(ArbitrageToken.values())
        .stream()
        .map(ArbitrageToken::getLabel)
        .map(t -> t.toLowerCase())
        .collect(Collectors.toSet());

    //#region Pattern Start/End Constants
    //(?<Complainant>.+?)(?=( #regex# ))
    private static final String PATTERN_COMPLAINANT_START = "(?<Complainant>.+?)(?=(";
    private static final String PATTERN_COMPLAINANT_END = "))";

    //(?<Defendant>.+?)(?=( #regex# ))
    private static final String PATTERN_DEFENDANT_START = "(?<Defendant>.+?)(?=(";
    private static final String PATTERN_DEFENDANT_END = "))";

    //(?<ThirdParty>.+?)(?=( #regex# ))
    private static final String PATTERN_THIRD_PARTY_START = "(?<ThirdParty>.+?)(?=(";
    private static final String PATTERN_THIRD_PARTY_END = "))";

    //(?<CompetitionManager>.+?)(?=( #regex# ))
    private static final String PATTERN_COMPETITION_MANAGER_START = "(?<CompetitionManager>.+?)(?=(";
    private static final String PATTERN_COMPETITION_MANAGER_END = "))";

    //(?<FinancialManager>.+?)(?=( #regex# ))
    private static final String PATTERN_FINANCIAL_MANAGER_START = "(?<FinancialManager>.+?)(?=(";
    private static final String PATTERN_FINANCIAL_MANAGER_END = "))";

    //(?<Keyword> #regex# )
    private static final String PATTERN_KEYWORD_START = "(?<Keyword>";
    private static final String PATTERN_KEYWORD_END = ")";
    //#endregion

    //#endregion

    String value;
    String innerRegex;
    String type;
    EntityMap related;
    boolean isProcessed;


    public Entity(String value, String type) {
        this.value = value;
        this.type = type;
        this.related = new EntityMap();

        this.isProcessed = false;
    }

    //#region Property Checking
    public boolean ofType(IToken type) {
        return this.type.equals(type.getLabel());
    }

    public boolean bothOfType(Entity otherEntity, IToken type) {

        boolean isOfType = this.ofType(type);
        boolean hasSameType = otherEntity.getType().equals(this.type);

        return  isOfType && hasSameType;
    }

    public boolean bothIsPerson(Entity otherEntity) {
        
        boolean isOfPersonType = this.ofType(ArbitrageToken.Complainant) ||
            this.ofType(ArbitrageToken.Defendant) ||
            this.ofType(ArbitrageToken.ThirdParty) ||
            this.ofType(ArbitrageToken.FinancialManager) ||
            this.ofType(ArbitrageToken.CompetitionManager);

        boolean hasSameType = otherEntity.getType().equals(this.type);

        return isOfPersonType && hasSameType;
    }

    public boolean isSpecial() {
        return Entity.SPECIAL_TOKEN_TYPE.contains(this.type.toLowerCase());
    }

    public boolean hasNamedRegexValue(IToken type) {
        boolean correctStartPattern = this.value.startsWith("(?<"+type.getLabel()+">");
        boolean correctEndPattern = this.value.endsWith(")"); 

        return  correctStartPattern && correctEndPattern;
    }

    public boolean hasPersonNamedRegexValue() {
        return this.hasNamedRegexValue(ArbitrageToken.Complainant) ||
                this.hasNamedRegexValue(ArbitrageToken.Defendant) ||
                this.hasNamedRegexValue(ArbitrageToken.ThirdParty) ||
                this.hasNamedRegexValue(ArbitrageToken.FinancialManager) ||
                this.hasNamedRegexValue(ArbitrageToken.CompetitionManager);
    }

    //#endregion

    //#region Getter/Setter
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        if (!RegExRepository.validateRegex(value)) {
            throw new IllegalArgumentException("Value must be correct regex value");
        }

        this.value = value;
    }

    public String getInnerRegex() {
        return this.innerRegex;
    }

    public void setInnerRegex(String innerRegex) {
        if (!RegExRepository.validateRegex(innerRegex)) {
            throw new IllegalArgumentException("Value must be correct regex value");
        }

        this.innerRegex = innerRegex;
    }

    public String getType() {
        return this.type;
    }

    public EntityMap getRelatedEntities() {
        return this.related;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed() {
        this.isProcessed = true;
    }
    //#endregion

    //#region Value Merging

    public Entity merge(Entity otherEntity) {
        
        if (this.bothIsPerson(otherEntity)) this.processPerson();
        if (this.bothOfType(otherEntity, ArbitrageToken.Keyword)) this.processKeyword();
        if (this.bothOfType(otherEntity, CommonToken.Word)) this.processWord();
        
        return this;
    }

    public Entity mergePerson(Entity otherEntity) {

        this.value = this.processPerson();
        otherEntity.value = otherEntity.processPerson();

        //(?<Complainant>.+?)(?=( #regex# ))
        String patternStart = "(?<"+this.type+">.+?)(?=(";
        String patternEnd = "))";

        Set<String> relatedWords = new HashSet<String>(Arrays.asList(otherEntity.innerRegex.split("\\|")));
        relatedWords.addAll(new HashSet<String>(Arrays.asList(this.innerRegex.split("\\|"))));

        this.innerRegex = String.join("|", relatedWords);
        this.value = patternStart + this.innerRegex + patternEnd;

        return this;
    }

    public Entity mergeKeyword(Entity otherEntity) {

        this.value = this.processKeyword();
        otherEntity.value = otherEntity.processKeyword();

        Set<String> keywords = new HashSet<String>(Arrays.asList(otherEntity.innerRegex.split("\\|")));
        keywords.addAll(new HashSet<String>(Arrays.asList(this.innerRegex.split("\\|"))));

        this.innerRegex = String.join("|", keywords);
        this.value = PATTERN_KEYWORD_START + this.innerRegex + PATTERN_KEYWORD_END;

        return this;
    }

    public Entity mergeWord(Entity otherEntity) {
        if (!this.isProcessed) this.processWord();
        if (!otherEntity.isProcessed) otherEntity.processWord();

        if (!this.value.equals(otherEntity.value)) throw new IllegalArgumentException("Words must have equal values to be merged");
        if (!this.innerRegex.equals(otherEntity.innerRegex)) throw new IllegalArgumentException("Words must have equal inner regex to be merged");
        
        System.out.println(this.value + " | " + otherEntity.value );
        System.out.println(this.innerRegex + " | " + otherEntity.innerRegex );

        this.getRelatedEntities().addAll(otherEntity.getRelatedEntities());

        return this;
    }
    //#endregion
    
    //#region Value Processing

    public String processPerson() {
        String entityValue = this.bracesAsRegex();

        //(?<Complainant>.+?)(?=( #regex# ))
        String patternStart = "(?<"+this.type+">.+?)(?=(";
        String patternEnd = "))";

        //#region Process Related Words
        Set<String> relatedWord = new HashSet<>();
        if (this.hasPersonNamedRegexValue()) {
            String entityRegex = entityValue.substring(patternStart.length(), entityValue.length() - patternEnd.length());
            entityRegex = this.removeKeywordPart(entityRegex);

            relatedWord.addAll(
                new HashSet<String>(
                    Arrays.asList(
                        entityRegex.split("\\|")
            )));
        }

        relatedWord.addAll(
            this.related.stream()
                .filter(e -> e.ofType(CommonToken.Word))
                .map(Entity::getValue)
                .collect(Collectors.toSet())
        );

        String innerRegexWord = String.join("|", relatedWord);
        //#endregion
        
        //#region Process Related Keywords
        Set<String> relatedKeyword = this.related.stream()
            .filter(e -> e.ofType(ArbitrageToken.Keyword))
            .map(Entity::getInnerRegex)
            .collect(Collectors.toSet());

        String innerRegexKeyword = String.join("|", relatedKeyword);
        //#endregion
        
        this.innerRegex = innerRegexWord;
        if (!relatedKeyword.isEmpty()) {
            this.innerRegex += "|" + PATTERN_KEYWORD_START + innerRegexKeyword + PATTERN_KEYWORD_END;
        }

        this.value = patternStart + this.innerRegex + patternEnd;

        return this.value;
    }

    public String processKeyword() {
        String entityValue = this.bracesAsRegex();

        // Length of string
        // "(?<Keyword>".length() -> 11
        if (this.hasNamedRegexValue(ArbitrageToken.Keyword)) {
            entityValue = entityValue.substring(PATTERN_KEYWORD_START.length(), entityValue.length() - PATTERN_KEYWORD_END.length());
        } else {
            entityValue = "(" + entityValue + ")";
        }

        this.innerRegex = String.join("|", entityValue);
        this.value = PATTERN_KEYWORD_START + this.innerRegex + PATTERN_KEYWORD_END;

        return this.value;
    }

    public String processWord() {
        String entityValue = this.bracesAsRegex().toLowerCase();
        
        this.innerRegex = entityValue;
        
        // Words shorter or equal than 3 letters 
        if (entityValue.length() <= 3) {
            entityValue = "(^|\\s*)" + entityValue + "($|\\s+)";
        }
        
        this.value = entityValue;

        return entityValue;
    }

    private String bracesAsRegex() {
        if (this.isProcessed) return this.value;

        this.isProcessed = true;

        return this.value
            .replaceAll("(?<!\\\\)\\(", "\\\\(")
            .replaceAll("(?<!\\\\)\\)", "\\\\)");
    }

    private String removeKeywordPart(String str) {
        Matcher m = Pattern.compile("[|][(][?][<]Keyword[>]").matcher(str);
        
        if (m.find()) return str.substring(0, m.start());
        return str;
    }
    //#endregion

    //#region Json Serialization
    public static Entity fromJsonObject(JSONObject json) {
            
        Entity entity = new Entity(
            json.getString("value"), 
            json.getString("type")
        );

        for (Object o : json.getJSONArray("related")) {
            JSONObject relEntityJson = (JSONObject) o;

            Entity relEntity = new Entity(
                relEntityJson.getString("value"), 
                relEntityJson.getString("type")
            );

            entity
            .getRelatedEntities()
            .add(relEntity);
        }

        return entity;
    }

    public JSONObject toJsonObject() {
        JSONObject entityJson = new JSONObject();

        entityJson
        .put("value", this.value)
        .put("type", this.type)
        .put("related", new JSONArray());

        for (Entity relEntity : this.related) {
            entityJson
            .getJSONArray("related")
            .put(relEntity.toJsonObject());
        }

        return entityJson;
    }
    //#endregion
    
    //#region Object Override
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Entity other = (Entity) obj;

        return Objects.equals(this.type, other.type) && 
            (Objects.equals(this.value, other.value) || this.isSpecial());
    }

    @Override
    public int hashCode() {
        if (this.isSpecial()) 
            return Objects.hash(this.type);
        else 
            return Objects.hash(this.value, this.type);
    }
  //#endregion
}
