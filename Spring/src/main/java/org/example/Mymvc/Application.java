package org.example.Mymvc;

import org.example.Mymvc.annotation.MyController;
import org.example.Mymvc.annotation.MyRequestMapping;

import java.lang.reflect.Method;

public class Application {

    public static void main(String[] args) throws Exception {
        // Simulating class scanning and registration
        Class<?>[] controllerClasses = {MyControllerClass.class};

        for (Class<?> controller : controllerClasses) {
            if (controller.isAnnotationPresent(MyController.class)) {
                for (Method method : controller.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(MyRequestMapping.class)) {
                        String path = method.getAnnotation(MyRequestMapping.class).value();
                        MyRouter.registerRoute(path, method);
                    }
                }
            }
        }

        // Normally, you'd start the server here, like with an embedded Jetty or Tomcat
    }
}