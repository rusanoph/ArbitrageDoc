package ru.idr.datamarkingeditor.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.EntityMap;
import ru.idr.datamarkingeditor.model.entity.utility.InitialEntity;
import ru.idr.datamarkingeditor.model.token.IToken;

public class TextParser {

    private EntityMap model;

    public TextParser(EntityMap model) { this.model = model; }
    public TextParser(JSONObject modelJson) { this(EntityMap.fromJsonObject(modelJson)); }
    public TextParser(String modelRawJson) { this(EntityMap.fromJsonObject(modelRawJson)); }

    @Deprecated
    public EntityMap getEM() {
        return this.model;
    }

    public List<Entity> parseEntities(String text) {
        List<Entity> foundEntities = new ArrayList<>();
        Entity current = this.model.get(new InitialEntity());

        while (current.getRelated().notEmpty()) {

            Entity foundEntity = null;
            Integer minMatcherIndexStart = Integer.MAX_VALUE;
            Integer minMatcherIndexEnd = Integer.MAX_VALUE;

            Boolean continueParsing = false;
            for (Entity nextRelated : current.getRelated()) {
                
                Matcher m = Pattern.compile(nextRelated.getValue(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE).matcher(text.toLowerCase());

                if (m.find()) {
                    // ...
                    
                    if (minMatcherIndexStart > m.start()) {
                        continueParsing = true;
                        minMatcherIndexStart = m.start();
                        minMatcherIndexEnd = m.end();

                        IToken foundType = nextRelated.getType();
                        if (foundType.notCommon()) {
                            foundEntity = Entity.createInstance(m.group(foundType.getLabel()), foundType);
                        }
                    }
                }
            }

            if (!continueParsing) break;

            String startsWithSpecialOrSpace = "^[:\\s,;!.-]+";
            text = text
                .substring(minMatcherIndexStart, text.length())
                .replaceFirst(startsWithSpecialOrSpace, "");

            if (foundEntity != null) foundEntities.add(foundEntity);
        }

        return foundEntities;
    }

    // public JSONObject parseEntitiesAsJson(String text) {
        // return this.parseEntities(text).toJsonObject();
    // }
}
