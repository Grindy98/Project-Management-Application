package persistent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistent.exception.ProjectValidationFailedException;
import persistent.exception.TaskValidationFailedException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    Task task;

    @BeforeEach
    void setUp() {
        try {
            task = new Task("assignee", new Task.SimpleDate(9, 1, 2021), "desc");
        } catch (TaskValidationFailedException e) {
            fail("Task should have been initialized properly");
        }
    }

    @Test
    void projectConstructor() {
        try {
            task = new Task("", new Task.SimpleDate(9, 1, 2021), "desc");
            fail("Task should throw error");
        } catch (TaskValidationFailedException e) {
            if (!e.getErrors().contains(TaskValidationFailedException.Type.SELECTOR_EMPTY)) {
                fail("Wrong exception");
            }
        }
        try {
            task = new Task("assignee", new Task.SimpleDate(9, 0, 2021), "desc");
            fail("Task should throw error");
        } catch (TaskValidationFailedException e) {
            if (!e.getErrors().contains(TaskValidationFailedException.Type.DATE)) {
                fail("Wrong exception");
            }
        }
        try {
            task = new Task("assignee", new Task.SimpleDate(9, 1, 2021), "");
            fail("Task should throw error");
        } catch (TaskValidationFailedException e) {
            if (!e.getErrors().contains(TaskValidationFailedException.Type.DESCRIPTION_LENGTH)) {
                fail("Wrong exception");
            }
        }
    }

    @Test
    void setDescription() {
        try {
            task.setDescription("");
            fail("Task should throw error");
        } catch (TaskValidationFailedException e) {
            if (!e.getErrors().contains(TaskValidationFailedException.Type.DESCRIPTION_LENGTH)) {
                fail("Wrong exception");
            }
        }
        try {
            task.setDescription("desc2");
        } catch (TaskValidationFailedException e) {
            fail("Task should initialize properly");
        }
    }

    @Test
    void setDeadline() {
        try {
            task.setDeadline(new Task.SimpleDate(0,-1, 0));
            fail("Task should throw error");
        } catch (TaskValidationFailedException e) {
            if (!e.getErrors().contains(TaskValidationFailedException.Type.DATE)) {
                fail("Wrong exception");
            }
        }
        try {
            task.setDeadline(new Task.SimpleDate(1,1, 2021));
        } catch (TaskValidationFailedException e) {
            fail("Task should initialize properly");
        }
    }

    @Test
    void setAssigneeUsername() {
        try {
            task.setAssigneeUsername(null);
            fail("Task should throw error");
        } catch (TaskValidationFailedException e) {
            if (!e.getErrors().contains(TaskValidationFailedException.Type.SELECTOR_EMPTY)) {
                fail("Wrong exception");
            }
        }
        try {
            task.setAssigneeUsername("user1");
        } catch (TaskValidationFailedException e) {
            fail("Task should initialize properly");
        }
    }
}