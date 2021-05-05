package scene;

import scene.controller.SceneController;
import scene.controller.implementations.MainPageController;
import scene.controller.implementations.RegistrationPageController;
import scene.controller.implementations.StartPageController;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum SceneType{
    START(StartPageController.class, true),
    REGISTER(RegistrationPageController.class, true),
    MAIN_PAGE(MainPageController.class, true),
    ;
    private final Constructor<? extends SceneController> sceneConstr;
    private final SceneController controller;

    SceneType(Class<? extends SceneController> scene, boolean reload){
        this.sceneConstr= (Constructor<? extends SceneController>) scene.getConstructors()[0];
        // If controller is null, then we reload it each time
        if(reload == false){
            try {
                controller = sceneConstr.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            controller = null;
        }
    }

    public SceneController getSceneController() {
        if(controller == null){
            try {
                return sceneConstr.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return controller;
    }
}
