package persistent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistent.exception.ProjectValidationFailedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    Project project;

    @BeforeEach
    void setUp() {
        try {
            project = new Project(List.of("user1, user2"), "owner", "proj", "desc");
        } catch (ProjectValidationFailedException e) {
            fail("Project should have been initialized properly");
        }
    }

    @Test
    void projectConstructor() {
        try {
            Project p = new Project(List.of(), "owner", "proj", "desc");
            fail("Project should throw error");
        } catch (ProjectValidationFailedException e) {
            if (!e.getErrors().contains(ProjectValidationFailedException.Type.MEMBERS_EMPTY)) {
                fail("Wrong exception");
            }
        }
        try {
            Project p = new Project(List.of("user1"), "owner", "", "desc");
            fail("Project should throw error");
        } catch (ProjectValidationFailedException e) {
            if (!e.getErrors().contains(ProjectValidationFailedException.Type.NAME_LENGTH)) {
                fail("Wrong exception");
            }
        }
    }

    @Test
    void setNameWithVal() {
        try {
            project.setNameWithVal("newName");
        } catch (ProjectValidationFailedException e) {
            fail("No exception should be thrown");
        }
        try {
            project.setNameWithVal("");
            fail("No exception should be thrown");
        } catch (ProjectValidationFailedException e) {
            if (!e.getErrors().contains(ProjectValidationFailedException.Type.NAME_LENGTH)) {
                fail("Wrong exception");
            }
        }
    }

    @Test
    void setDescriptionWithVal() {
        try {
            project.setDescriptionWithVal("newDesc");
        } catch (ProjectValidationFailedException e) {
            fail("No exception should be thrown");
        }
    }
}