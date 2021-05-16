package persistent;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistent.exception.PersistException;
import persistent.exception.ProjectValidationFailedException;
import persistent.service.FileSystemHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Project {

    private static ObjectMapper mapper;

    private static final ObservableList<Project> projects;
    static {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        projects = FXCollections.observableArrayList();
    }

    private List<String> memberUsernameList;
    private String ownerUsername;
    private String name;
    private String description;
    @JsonIgnore
    private ObservableList<Task> tasks;

    public Project(List<String> memberUsernameList, String ownerUsername, String name, String description)
            throws ProjectValidationFailedException {
        this.memberUsernameList = memberUsernameList;
        this.ownerUsername = ownerUsername;
        this.name = name;
        this.description = description;
        // Empty list at first;
        this.tasks = FXCollections.observableArrayList();
        validate();
    }

    private Project(){}

    @JsonGetter("tasks-list")
    private List<Task> getTasksSerialize(){
        return new ArrayList<>(tasks);
    }

    @JsonSetter("tasks-list")
    private void setTasksSerialize(List<Task> tasks){
        this.tasks = FXCollections.observableArrayList(tasks);
    }

    public List<String> getMemberUsernameList() {
        return memberUsernameList;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getName() {
        return name;
    }

    public void setNameWithVal(String name) throws ProjectValidationFailedException {
        this.name = name;
        validate();
    }

    public String getDescription() {
        return description;
    }

    public ObservableList<Task> getTasks(){
        return tasks;
    }

    public void setDescriptionWithVal(String description) throws ProjectValidationFailedException {
        this.description = description;
        validate();
    }

    public static void load(){
        projects.clear();
        try {
            projects.addAll(mapper.readValue(FileSystemHandler.FileType.PROJECT.getSavePath().toFile(),
                    new TypeReference<List<Project>>(){}));
        } catch (IOException e) {
            throw new PersistException("Loading project from file failed", e);
        }
    }

    public static void save(){
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(
                    FileSystemHandler.FileType.PROJECT.getSavePath().toFile(), projects);
        } catch (IOException e) {
            throw new PersistException("Saving project to file failed", e);
        }
    }

    public static ObservableList<Project> getProjects(){
        return projects;
    }

    private void validate() throws ProjectValidationFailedException {
        //validate user list + owner
        ProjectValidationFailedException exc = new ProjectValidationFailedException();
        if(name.length() > 50 || name.length() < 1){
            exc.addError(ProjectValidationFailedException.Type.NAME_LENGTH);
        }
        if(name.matches("[^[A-Za-z0-9]\\s]")){
            exc.addError(ProjectValidationFailedException.Type.NAME_CONTENT);
        }
        if(description.length() > 1000){
            exc.addError(ProjectValidationFailedException.Type.DESCRIPTION_LENGTH);
        }
        if(memberUsernameList.size() == 0){
            exc.addError(ProjectValidationFailedException.Type.MEMBERS_EMPTY);
        }
        if(!exc.getErrors().isEmpty()){
            throw exc;
        }
    }

    @Override
    public String toString() {
        return "Project{" +
                "memberUsernameList=" + memberUsernameList +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
