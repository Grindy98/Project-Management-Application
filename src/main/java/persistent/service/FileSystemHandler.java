package persistent.service;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemHandler {

    private static final String APPLICATION_FOLDER = ".project-management-app";
    private static final String USER_FOLDER = System.getProperty("user.home");

    public static final Path APP_FULL_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);

    public static Path getPathToFile(String... path) {
        return APP_FULL_PATH.resolve(Paths.get(".", path));
    }
}
