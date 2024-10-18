package org.example.Mymvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MyRouter {
    private static Map<String, Method> routeMappings = new HashMap<>();

    public static void registerRoute(String path, Method method) {
        routeMappings.put(path, method);
    }

    public static void handleRequest(String path, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Method method = routeMappings.get(path);
        if (method != null) {
            Object controller = method.getDeclaringClass().newInstance();
            method.invoke(controller, req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
        }
    }
}

