package gr.rapti.project.recipes.core.exceptions;

public class EntityNotAuthorized extends EntityGenericException{

    private static final String DEFAULT_CODE = "NotAuthorized";

    public EntityNotAuthorized(String code,String message){
        super(code + DEFAULT_CODE, message);
    }
}
