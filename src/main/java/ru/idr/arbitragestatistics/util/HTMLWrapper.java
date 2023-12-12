package ru.idr.arbitragestatistics.util;

public class HTMLWrapper {
    
    public static String tag(String tag, String text, String cssClass, String id) {
        return String.format("<%s id='%s' class='%s'>%s</%s>", tag, id, cssClass, text, tag);
    }
    
    public static String tag(String tag, String text, String cssClass) {
        return tag(tag, text, cssClass, "");
    }

    public static String tag(String tag, String text) {
        return tag(tag, text, "");
    }
   
    
}
