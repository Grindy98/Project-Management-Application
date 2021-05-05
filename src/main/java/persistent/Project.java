package persistent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
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
    /*tasks
    */

    public Project(List<String> memberUsernameList, String ownerUsername, String name, String description)
            throws ProjectValidationFailedException {
        this.memberUsernameList = memberUsernameList;
        this.ownerUsername = ownerUsername;
        this.name = name;
        this.description = description;
        validate();
    }

    private Project(){

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

    public void setDescriptionWithVal(String description) throws ProjectValidationFailedException {
        this.description = description;
        validate();
    }

    public static void load(){
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
        if(name.length() > 40 || name.length() < 1){
            throw new ProjectValidationFailedException(
                    "Name must be between 1 and 40 characters");
        }

        if(name.matches("[^\\w\\s]")){
            throw new ProjectValidationFailedException(
                    "Invalid name character");
        }

        if(description.length() > 1000){
            throw new ProjectValidationFailedException(
                    "Description must be smaller than 1000 characters");
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
