package ru.idr.datamarkingeditor.model.token;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface IToken {
    //#region IToken Inheritors Classes (Static)
    public static final List<Class<?>> tokenEnumClasses = 
        List.of(
            ArbitrageToken.class, 
            CommonToken.class
        );
    //#endregion

    public String getLabel();
    
    default public boolean isPerson() { return false; };
    default public boolean isKeyword() { return false; };
    default public boolean notCommon() { return true; }; // In CommonToken enum returns false
    
    public static IToken fromString(String rawToken) { 

        try {
            for (var tokenClass : IToken.tokenEnumClasses) {
                IToken[] tokens = (IToken[]) tokenClass.getMethod("values").invoke(null);
                
                boolean foundTokenClass = 
                    Set.of(tokens)
                        .stream()
                        .map(IToken::getLabel)
                        .collect(Collectors.toSet())
                        .contains(rawToken);
                
                if (foundTokenClass) return (IToken) tokenClass.getMethod("valueOf", String.class).invoke(null, rawToken); 
            }
        } catch (Exception ex) {
            // ... After this code block will be thrown the exception ...
        }
        
        throw new IllegalArgumentException("Token with value '" + rawToken + "' does not exists.");
    };
}
