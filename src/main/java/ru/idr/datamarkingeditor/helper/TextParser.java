package ru.idr.datamarkingeditor.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.EntityMap;
import ru.idr.datamarkingeditor.model.entity.utility.InitialEntity;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class TextParser {

    private EntityMap model;
    private List<JSONObject> path = new ArrayList<>();

    public TextParser(EntityMap model) { this.model = model; }
    public TextParser(JSONObject modelJson) { this(EntityMap.fromJsonObject(modelJson)); }
    public TextParser(String modelRawJson) { this(EntityMap.fromJsonObject(modelRawJson)); }

    @Deprecated
    public EntityMap getEM() {
        return this.model;
    }

    public List<Entity> parseEntities(String text) {
        this.path.clear();
        text = text.replaceAll("\\s+", " ");

        List<Entity> foundEntities = new ArrayList<>();
        Entity current = this.model.get(new InitialEntity());

        while (current.getRelated().notEmpty()) {
            JSONObject currentPathStep = new JSONObject();

            Entity foundEntity = null;
            Integer minMatcherIndexStart = Integer.MAX_VALUE;
            Integer minMatcherIndexEnd = Integer.MAX_VALUE;

            Boolean continueParsing = false;
            EntityMap currentRelated = current.getRelated();
            for (Entity next : currentRelated) {
                
                Matcher m = Pattern.compile(next.getValue(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE).matcher(text.toLowerCase());

                if (m.find()) {
                    // ...
                    
                    if (minMatcherIndexStart > m.start()) {
                        continueParsing = true;
                        minMatcherIndexStart = m.start();
                        minMatcherIndexEnd = m.end();

                        current = next;
                        currentPathStep = new JSONObject()
                            .put("value", m.group())
                            .put("start", m.start())
                            .put("end", m.end());

                        IToken foundType = next.getType();
                        if (foundType != CommonToken.Word) {
                            foundEntity = Entity.createInstance(m.group(foundType.getLabel()), foundType);
                        }
                    }
                }
            }

            if (!continueParsing) break;

            String startsWithSpecialOrSpace = "^[:\\s,;!.-]+";
            text = text
                .substring(minMatcherIndexEnd, text.length())
                .replaceFirst(startsWithSpecialOrSpace, "");
            
            if (foundEntity != null) foundEntities.add(foundEntity);
            this.path.add(currentPathStep);
        }

        return foundEntities;
    }


    public JSONArray getPathAsJson() {
        return new JSONArray(this.path);
    }

    public JSONArray parseEntitiesAsJson(String text) {
        List<Entity> foundEntities = this.parseEntities(text);
        
        JSONArray foundEntitiesJson = new JSONArray();
        for (Entity entity : foundEntities) {
            JSONObject json = new JSONObject()
                .put("value", entity.getRawValue())
                .put("type", entity.getType().getLabel());
            foundEntitiesJson.put(json);
        }

        return foundEntitiesJson; 
    }
}
