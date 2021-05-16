package persistent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

    private String assigneeUsername;
    private SimpleDate deadline;
    private String review;
    private String description;
    private Boolean isCompleted;
    @JsonIgnore
    private Project owner = null;

    public Task(String assigneeUsername, SimpleDate deadline, String description, String projectName, Boolean isCompleted){
        this.assigneeUsername = assigneeUsername;
        this.deadline = deadline;
        this.description = description;
        this.isCompleted = isCompleted;
        review = null;
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

    public static class SimpleDate{
        int year;
        int month;
        int day;

        public SimpleDate(int day, int month, int year){
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public SimpleDate(String date){
            String[] dateElements = date.split("/");
            day = Integer.parseInt(dateElements[0]);
            month = Integer.parseInt(dateElements[1]);
            year = Integer.parseInt(dateElements[2]);
        }

        private SimpleDate(){}

        @Override
        public String toString(){
            return day + "/" + month + "/" + year;
        }
    }
}
