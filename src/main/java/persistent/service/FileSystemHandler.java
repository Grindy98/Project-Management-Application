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
        PROJECT("/save_models/empty_save.json",
            "project"),
        USER("/save_models/empty_save.json",
            "users"),
        ;

        private Path savePath;
        private static String folderName = "config";

        private final String emptyLocation;
        private final String saveFileName;

        FileType(String emptyLocation, String saveFileName){
            this.emptyLocation = emptyLocation;
            this.saveFileName = saveFileName;
        }

        public Path getSavePath(){
            Path savePath = FileSystemHandler.getPathToFile(folderName, saveFileName);
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
            return savePath;
        }
    }

    private static final String APPLICATION_FOLDER = ".project-management-app";
    private static final String USER_FOLDER = System.getProperty("user.home");

    public static final Path APP_FULL_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);

    public static void changeFolderName(String newName){
        FileType.folderName = newName;
    }

    private static Path getPathToFile(String... path) {
        return APP_FULL_PATH.resolve(Paths.get(".", path));
    }
}
