package persistent.user;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import persistent.service.FileSystemHandler;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static persistent.service.FileSystemHandler.changeFolderName;

class UserSaveTest {

    private static final String emptyLocation = "/save_models/empty_save.json";
    private static final String testSave = "/user/test_save.json";


    private void printToCons(File f){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void copyToSaveFile(String from){
        // Copy file from path
        try {
            URL emptyURL = this.getClass().getResource(from);
            if(emptyURL == null){
                throw new IllegalArgumentException("Empty file not found!");
            }
            System.out.println("before");
            printToCons(FileSystemHandler.FileType.USER.getSavePath().toFile());
            FileUtils.copyFile(new File(emptyURL.toURI()),
                    FileSystemHandler.FileType.USER.getSavePath().toFile());
            System.out.println("after");
            printToCons(FileSystemHandler.FileType.USER.getSavePath().toFile());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setUp() {
        changeFolderName("test");
    }
    @Test
    void load(){
        copyToSaveFile(testSave);
        User.load();
        List<User> list = List.copyOf(User.getUsers().values());
        assertEquals(1, list.size());
        // One proj loaded
        User u = list.get(0);
        assertEquals("gorrocks2s", u.getUsername());
        assertEquals("18 Myrtle Center", u.getAddress());
        assertEquals("7899691807", u.getPhone());
        assertTrue(u instanceof TeamMember);
    }

    @Test
    void save(){
        copyToSaveFile(emptyLocation);
        User.load();
        User u = new TeamMember("test_user", "12345678", "9 Maple Drive", "0123798213");
        User.getUsers().put(u.getUsername(), u);
        User.save();
        // File increased in size
        assertTrue(FileUtils.sizeOf(new File(UserSaveTest.class.getResource(emptyLocation).getFile())) <
                FileUtils.sizeOf(FileSystemHandler.FileType.USER.getSavePath().toFile()));
    }

    @Test
    void saveThenLoad(){
        copyToSaveFile(emptyLocation);
        User.load();
        System.out.println(User.getUsers());
        User u = new TeamMember("test_user", "12345678", "9 Maple Drive", "0123798213");
        User.getUsers().put(u.getUsername(), u);
        User.save();
        // File increased in size
        assertTrue(FileUtils.sizeOf(new File(UserSaveTest.class.getResource(emptyLocation).getFile())) <
                FileUtils.sizeOf(FileSystemHandler.FileType.USER.getSavePath().toFile()));
        User.load();
        List<User> list = List.copyOf(User.getUsers().values());
        assertEquals(1, list.size());
        // One proj loaded
        u = list.get(0);
        assertEquals("test_user", u.getUsername());
        assertTrue(u.checkPassword("12345678"));
        assertEquals("9 Maple Drive", u.getAddress());
        assertEquals("0123798213", u.getPhone());
        assertTrue(u instanceof TeamMember);
    }

    @AfterAll
    static void tearDown(){
        FileSystemHandler.changeFolderName("config");
    }

}