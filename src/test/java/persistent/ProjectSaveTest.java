package persistent;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import persistent.exception.ProjectValidationFailedException;
import persistent.service.FileSystemHandler;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static persistent.service.FileSystemHandler.*;

class ProjectSaveTest {

    private static final String emptyLocation = "/save_models/empty_save.json";
    private static final String testSave = "/project/test_save.json";

    private void copyToSaveFile(String from){
        // Copy file from path
        try {
            URL emptyURL = this.getClass().getResource(from);
            if(emptyURL == null){
                throw new IllegalArgumentException("Empty file not found!");
            }
            FileUtils.copyFile(new File(emptyURL.toURI()),
                    FileType.PROJECT.getSavePath().toFile());
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
        Project.load();
        List<Project> list = Project.getProjects();
        assertEquals(1, list.size());
        // One proj loaded
        Project p = list.get(0);
        assertEquals(5, p.getMemberUsernameList().size());
        assertEquals("me", p.getOwnerUsername());
        assertEquals("testProject", p.getName());
        assertEquals("testDesc", p.getDescription());
    }

    @Test
    void save(){
        copyToSaveFile(emptyLocation);
        Project.load();
        Project p = null;
        try {
            p = new Project(List.of("user1"), "user2", "name", "desc");
        } catch (ProjectValidationFailedException e) {
            fail("No exception should be thrown");
        }
        Project.getProjects().add(p);
        Project.save();
        // File increased in size
        assertTrue(FileUtils.sizeOf(new File(ProjectSaveTest.class.getResource(emptyLocation).getFile())) <
                FileUtils.sizeOf(FileType.PROJECT.getSavePath().toFile()));
    }

    @Test
    void saveThenLoad(){
        copyToSaveFile(emptyLocation);
        Project.load();
        Project p = null;
        try {
            p = new Project(List.of("user1"), "user2", "name", "desc");
        } catch (ProjectValidationFailedException e) {
            fail("No exception should be thrown");
        }
        Project.getProjects().add(p);
        Project.save();
        // File increased in size
        assertTrue(FileUtils.sizeOf(new File(ProjectSaveTest.class.getResource(emptyLocation).getFile())) <
                FileUtils.sizeOf(FileType.PROJECT.getSavePath().toFile()));
        Project.load();
        List<Project> list = Project.getProjects();
        assertEquals(1, list.size());
        // One proj loaded
        Project read_p = list.get(0);

        assertTrue(read_p.getMemberUsernameList().containsAll(p.getMemberUsernameList()) &&
                p.getMemberUsernameList().containsAll(read_p.getMemberUsernameList()));
        assertEquals(p.getOwnerUsername(), read_p.getOwnerUsername());
        assertEquals(p.getName(), read_p.getName());
        assertEquals(p.getDescription(), read_p.getDescription());
    }

    @AfterAll
    static void tearDown(){
        FileSystemHandler.changeFolderName("config");
    }
}