package persistent.exception;

import java.util.ArrayList;

public class TaskValidationFailedException extends Exception {
    public enum Type{
        DATE("Date must respect the given format!"),
        DESCRIPTION_LENGTH("Task description must have between 1 and 50 characters!"),
        SELECTOR_EMPTY("No assignee chosen!"),
        ;
        String error;
        Type(String error){
            this.error = error;
        }

        public String getError(){
            return error;
        }
    }

    private ArrayList<TaskValidationFailedException.Type> errors;

    public TaskValidationFailedException(){
        errors = new ArrayList<>();
    }

    public void addError(TaskValidationFailedException.Type error){
        errors.add(error);
    }

    public ArrayList<TaskValidationFailedException.Type> getErrors() {
        return errors;
    }
}
