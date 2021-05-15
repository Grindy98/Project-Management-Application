package persistent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistent.exception.PersistException;
import persistent.service.FileSystemHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Task {
    private static ObjectMapper mapper;

    private static final ObservableList<Task> tasks;

    static {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        tasks = FXCollections.observableArrayList();
    }

    private String assigneeUsername;
    private SimpleDate deadline;
    private String review;
    private String description;
    private String projectName;
    private Boolean isCompleted;

    public Task(String assigneeUsername, SimpleDate deadline, String description, String projectName, Boolean isCompleted){
        this.assigneeUsername = assigneeUsername;
        this.deadline = deadline;
        this.description = description;
        this.projectName = projectName;
        this.isCompleted = isCompleted;

        review = "No review added.";
    }

    private Task(){}

    public void setReview(String review){
        this.review = review;
    }

    public static void load(){
        try {
            tasks.addAll(mapper.readValue(FileSystemHandler.FileType.TASK.getSavePath().toFile(),
                    new TypeReference<List<Task>>(){}));
        } catch (IOException e) {
            throw new PersistException("Loading project from file failed", e);
        }
    }

    public static void save(){
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(
                    FileSystemHandler.FileType.TASK.getSavePath().toFile(), tasks);
        } catch (IOException e) {
            throw new PersistException("Saving project to file failed", e);
        }
    }

    @Override
    public String toString() {
        return "Project{" +
                "assigneeUsername=" + assigneeUsername +
                ", deadline='" + deadline + '\'' +
                ", review='" + review + '\'' +
                ", description='" + description + '\'' +
                ", projectName='" + projectName + '\'' +
                ", isCompleted='" + isCompleted + '\'' +
                '}';
    }

    public static ObservableList<Task> getTasks(){return tasks;}

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

    public String getProjectName(){return projectName;}

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
