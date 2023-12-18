package ru.idr.arbitragestatistics.util.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.IJsonSerializable;

public class Tree<T> implements IJsonSerializable {
    private Integer depth = 0;
    private T value;
    private List<Tree<T>> children = new ArrayList<Tree<T>>();
    private Function<String, String> action;

    //#region Constructor
    public Tree(T value) {
        this.value = value;
    }
    
    public Tree(T value, List<Tree<T>> children) {
        this.value = value;
        this.children = children;
    }
    //#endregion

     //#region Getter / Setter
    public int getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public Function<String, String> getAction() {
        return action;
    }

    private int getVertexId() {
        return this.hashCode();
    }
    //#endregion

    public Tree<T> appendChild(Tree<T> child) {
        // System.out.println(" ----------------- New Node start -----------------");
        // System.out.println("parent=" + this.getVertexId() + "; value=" + this.getValue()+"; d="+this.getDepth());

        child.setDepth(this.depth + 1);
        this.children.add(child);
        
        // System.out.println("child=" + child.getVertexId() + "; value=" + child.getValue()+"; d="+child.getDepth());
        
        return this;
    }

    public void recomputeDepthDFS() {

        for (Tree<T> child : children) {
            child.setDepth(this.depth + 1);

            child.recomputeDepthDFS();
        }
    }

    //#region Action 
    public Tree<T> setAction(Function<String, String> action) {
        this.action = action;

        return this;
    }

    public boolean hasAction() {
        return this.action != null;
    }

    public String doAction(String text) {
        return this.action.apply(text);
    }
    //#endregion

    //#region Standard Actions 

    public static String parseComplainant(String text) {
        String ogrn = RegExRepository.regexOgrn + "|" + RegExRepository.regexOgrnip;                
        String inn = RegExRepository.regexInnLegalEntity + "|" + RegExRepository.regexInnPerson;

        Matcher match = Pattern.compile(String.format(".*?\\s*\\((%s),\\s*(%s)\\)", ogrn, inn)).matcher(text);

        String result = text;
        if (match.find()) {
            result = match.group();
        }
        
        return result;
    }

    //#endregion

    //#region Json Serialization

    public JSONObject vertexToJsonObject() {
        JSONObject vertexJson = new JSONObject();

        vertexJson.put("id", this.getVertexId())
                .put("value", this.getValue())
                .put("depth", this.getDepth())
                .put("hasAction", this.hasAction());

        return vertexJson;
    }

    @Override 
    public JSONArray verticesToJsonArray() {
        JSONObject parentVertexJson = this.vertexToJsonObject();

        return this.subVerticesToJsonArray().put(parentVertexJson);
    }
    
    // This method, because of recurcive structure, return all vertices
    // except parent vertex
    private JSONArray subVerticesToJsonArray() {
        JSONArray verticesJson = new JSONArray();

        for (Tree<T> subTree : children) {
            verticesJson.put(subTree.vertexToJsonObject());

            if (subTree.getChildren().size() > 0) {
                JSONArray subTreeChildrenJson = subTree.subVerticesToJsonArray();

                for (int i = 0; i < subTreeChildrenJson.length(); i++) {
                    verticesJson.put(subTreeChildrenJson.getJSONObject(i));
                }
            }
        }

        return verticesJson;
    }

    @Override
    public JSONArray edgesToJsonArray() {
        JSONArray edgesJson = new JSONArray();

        for (Tree<T> subTree : children) {
            JSONObject edgeJson = new JSONObject();

            edgeJson.put("source", this.getVertexId())
                .put("target", subTree.getVertexId());

            edgesJson.put(edgeJson);

            if (subTree.getChildren().size() > 0) {
                JSONArray subTreeChildrenJson = subTree.edgesToJsonArray();

                for (int i = 0; i < subTreeChildrenJson.length(); i++) {
                    edgesJson.put(subTreeChildrenJson.getJSONObject(i));
                }
            }
        }

        return edgesJson;
    }

    //#endregion


    //#region ToString Override
    @Override
    public String toString() {
        String result = "* " + value.toString() + "\n";

        for (Tree<T> child : children) {
            result += child.toString(1);
        }

        return result;
    }

    private String toString(int d) {
        String indent = "&#8195;".repeat(d);

        String result = indent + "* " + value.toString() + "; d = " + this.getDepth() + "\n";

        for (Tree<T> child : children) {
            result += child.toString(d + 1);
        }

        return result;
    }
    //#endregion
}
