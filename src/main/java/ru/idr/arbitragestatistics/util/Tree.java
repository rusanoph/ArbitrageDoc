package ru.idr.arbitragestatistics.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Tree<T> {
    private int deepth = 0;
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
    public int getDeepth() {
        return deepth;
    }

    public void setDeepth(int deepth) {
        this.deepth = deepth;
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

    
    //#endregion

    // #region Action 
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

    public Tree<T> appendChild(Tree<T> child) {
        child.setDeepth(deepth + 1);
        children.add(child);

        return this;
    }

    @Override
    public String toString() {
        String result = "* " + value.toString() + "\n";

        for (Tree<T> child : children) {
            result += child.toString(1);
        }

        return result;
    }

    public String toString(int deepth) {
        String indent = "\t".repeat(deepth);

        String result = indent + "* " + value.toString() + "\n";

        for (Tree<T> child : children) {
            result += child.toString(deepth + 1);
        }

        return result;
    }

    public Function<String, String> getAction() {
        return action;
    }
    
}
