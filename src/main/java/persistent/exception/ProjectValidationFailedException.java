package persistent.exception;

import java.util.ArrayList;

public class ProjectValidationFailedException extends Exception {

    public ProjectValidationFailedException(String message){
        super(message);
    }
}
