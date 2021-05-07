package persistent.exception;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ProjectValidationFailedException extends Exception {
    public enum Type{
        NAME_LENGTH("Name must be between 1 and 50 characters"),
        NAME_CONTENT("Name must only contain alphanumeric characters"),
        DESCRIPTION_LENGTH("Description must have less than 1000 characters"),
        MEMBERS_EMPTY("Project must have at least one member"),
        ;
        String error;
        Type(String error){
            this.error = error;
        }

        public String getError(){
            return error;
        }
    }

    private ArrayList<Type> errors;

    public ProjectValidationFailedException(){
        errors = new ArrayList<>();
    }

    public void addError(Type error){
        errors.add(error);
    }

    public ArrayList<Type> getErrors() {
        return errors;
    }
}
