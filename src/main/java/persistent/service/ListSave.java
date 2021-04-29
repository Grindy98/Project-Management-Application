package persistent.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.apache.commons.io.FileUtils;
import java.util.AbstractList;
import java.util.List;

public class ListSave<T> extends AbstractList<T> {

    private List<T> list;
    private Class<T> classType;

    private final Path SAVE_PATH;
    private ObjectMapper objectMapper;

    public ListSave(Class<T> classType, String saveFileName, String emptyFilePath){
        this.classType = classType;
        SAVE_PATH = FileSystemHandler.getPathToFile("config", saveFileName);
        if(!Files.exists(SAVE_PATH)){
            // Copy file from path
            try {
                URL emptyURL = this.getClass().getResource(emptyFilePath);
                if(emptyURL == null){
                    throw new IllegalArgumentException("Empty file not found!");
                }
                FileUtils.copyFile(new File(emptyURL.toURI()),
                        SAVE_PATH.toFile());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }

        }
        PolymorphicTypeValidator ptv =
                BasicPolymorphicTypeValidator.builder()
                        .allowIfSubType("persistent.")
                        .build();
        objectMapper = JsonMapper.builder()
            .activateDefaultTyping(ptv)
            .build();
        load();
    }

    public void load(){
        try {
            JavaType type = objectMapper.getTypeFactory().
                    constructCollectionType(List.class, classType);
            list = objectMapper.readValue(SAVE_PATH.toFile(), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(){
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(SAVE_PATH.toFile(), list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }
}
