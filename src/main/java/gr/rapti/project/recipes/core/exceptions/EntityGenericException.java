package gr.rapti.project.recipes.core.exceptions;

import lombok.Getter;

@Getter
public class EntityGenericException extends Exception{

    private final String code;

    public EntityGenericException(String code,String message){
        super(message);
        this.code = code;
    }

}
