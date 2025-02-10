package gr.rapti.project.recipes.core.exceptions;

public class EntityAlreadyExistsException extends EntityGenericException{

    private static final String DEFAULT_CODE = "AlreadyExists";

    public EntityAlreadyExistsException(String code,String message){
        super(code + DEFAULT_CODE, message);
    }

}
