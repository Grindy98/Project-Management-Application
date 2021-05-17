package persistent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import persistent.exception.TaskValidationFailedException;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.util.concurrent.TimeUnit.DAYS;

public class Task {

    private String assigneeUsername;
    private SimpleDate deadline;
    private String review;
    private String description;
    private Boolean isCompleted;
    @JsonIgnore
    private Project owner = null;

    public Task(String assigneeUsername, SimpleDate deadline, String description, Boolean isCompleted)
        throws TaskValidationFailedException {
        this.assigneeUsername = assigneeUsername;
        this.deadline = deadline;
        this.description = description;
        this.isCompleted = isCompleted;
        review = null;
        validate();
    }

    private Task(){}

    public void setReview(String review){
        this.review = review;
    }

    @Override
    public String toString() {
        return "Project{" +
                "assigneeUsername=" + assigneeUsername +
                ", deadline='" + deadline + '\'' +
                ", review='" + review + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted='" + isCompleted + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public String getAssigneeUsername() {
        return assigneeUsername;
    }

    public SimpleDate getDeadline() {
        return deadline;
    }

    public String getReview() {
        return review;
    }

    @JsonIgnore
    public Project getProject(){
        if(owner == null){
            // search for parent project and set it for future references
            for (Project p : Project.getProjects()) {
                if (p.getTasks().contains(this)) {
                    owner = p;
                    return owner;
                }
            }
        }
        return owner;
    }

    public Boolean getIsCompleted(){return isCompleted;}

    public void setDescription(String description){this.description = description;}

    public void setDeadline(SimpleDate deadline) {
        this.deadline = deadline;
    }

    public void setAssigneeUsername(String assigneeUsername){this.assigneeUsername = assigneeUsername;}

    public void setIsCompleted(Boolean isCompleted){this.isCompleted = isCompleted;}

    private void validate() throws TaskValidationFailedException{
        TaskValidationFailedException e = new TaskValidationFailedException();
        if(assigneeUsername == null || "".equals(assigneeUsername )){
            e.addError(TaskValidationFailedException.Type.SELECTOR_EMPTY);
        }
        if(description == null || description.length() > 50 || description.length() < 1){
            e.addError(TaskValidationFailedException.Type.DESCRIPTION_LENGTH);
        }
        if(deadline == null || !deadline.validate()){
            e.addError(TaskValidationFailedException.Type.DATE);
        }
        if(!e.getErrors().isEmpty()){
            throw e;
        }
    }

    public static class SimpleDate{
        int year;
        int month;
        int day;

        private SimpleDate(int day, int month, int year){
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public boolean validate(){
            try {
                LocalDate.of(year, Month.of(month), day);
            }catch(DateTimeException e){
                return false;
            }
            return true;
        }

        public SimpleDate(String date){
            String[] dateElements = date.split("/");
            day = Integer.parseInt(dateElements[0]);
            month = Integer.parseInt(dateElements[1]);
            year = Integer.parseInt(dateElements[2]);
        }

        @JsonIgnore
        public LocalDate getLocalDate(){
            return LocalDate.of(year, month, day);
        }

        @JsonIgnore
        public long getRemainingDays(){
            LocalDateTime t = LocalDate.now().atStartOfDay();
            LocalDateTime t2 = getLocalDate().atStartOfDay();
            return Duration.between(t, t2).toDays();
        }

        private SimpleDate(){}

        @Override
        public String toString(){
            return day + "/" + month + "/" + year;
        }
    }
}
