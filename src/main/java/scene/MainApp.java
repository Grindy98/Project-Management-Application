package scene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistent.TestPersistent;
import persistent.TestPersistentSubclass;
import scene.controller.implementations.StartPageController;
import scene.controller.implementations.RegistrationPageController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import user.ProjectManager;
import user.TeamMember;
import user.User;
import user.TeamMember;
import user.ProjectManager;
import user.utils.Encryptor;

public class MainApp extends Application{

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new RegistrationPageController().getScene();
        stage.setScene(scene);
        stage.show();

       /* TestPersistent t = new TestPersistent(5, "me");
        TestPersistent t2 = new TestPersistent(7, "me3");
        TestPersistent t3 = new TestPersistentSubclass(5, "me2", "fi21f9bwq");

        ArrayList<TestPersistent> arr = TestPersistent.load();
        System.out.println(arr);
        arr = new ArrayList<>(3);
        arr.add(t);
        arr.add(t2);
        arr.add(t3);
        System.out.println(arr);

        TestPersistent.save(arr);

        User user1 = new TeamMember("abcd", "pasdaf", "str a", "01741032");
        User user2 = new ProjectManager("eee", "gfh", "str b", "01111111");
        User user3 = new TeamMember("oooo", "ppppp", "str c", "017409900");

        ArrayList<User> arr = User.load();
        System.out.println(arr);
        arr.add(user1);
        User user4 = new TeamMember("ndd", "qqqqq", "str d", "0000000");

        arr.add(user4);
        arr.add(user3);
        //System.out.println(arr);
        User.save(arr);*/
        User u1 = new TeamMember("anc", "aa1e2a", "aaa", "0911");
        User u2 = new ProjectManager("anc", "aaa", "aaa", "0911");
        if(u1.equals(u2))
            System.out.println("equal");
        else
            System.out.println("not equal");

    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        System.out.println(RegistrationPageController.memorizedUsers);
        User.save(RegistrationPageController.memorizedUsers);
    }

    public static void main(String[] args) {
        launch(args);
    }
}