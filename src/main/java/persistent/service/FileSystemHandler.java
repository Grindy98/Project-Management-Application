package persistent.service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemHandler {

    public enum FileType{
        TEST("/save_models/empty_save.json",
                "testPersistent"),
        //PROJECT,
        USER("/save_models/empty_save.json", "users");

        private final Path savePath;

        FileType(String emptyLocation, String saveFileName){
            savePath = FileSystemHandler.getPathToFile("config", saveFileName);
            if(!Files.exists(savePath)){
                // Copy file from path
                try {
                    URL emptyURL = this.getClass().getResource(emptyLocation);
                    if(emptyURL == null){
                        throw new IllegalArgumentException("Empty file not found!");
                    }
                    FileUtils.copyFile(new File(emptyURL.toURI()),
                            savePath.toFile());
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        public Path getSavePath(){
            return savePath;
        }
    }

    private static final String APPLICATION_FOLDER = ".project-management-app";
    private static final String USER_FOLDER = System.getProperty("persistent.user.home");

    public static final Path APP_FULL_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);

    static Path getPathToFile(String... path) {
        return APP_FULL_PATH.resolve(Paths.get(".", path));
    }
}
