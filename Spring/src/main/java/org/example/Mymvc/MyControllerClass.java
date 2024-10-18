package org.example.Mymvc;

import org.example.Mymvc.annotation.MyController;
import org.example.Mymvc.annotation.MyRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MyController
public class MyControllerClass {

    @MyRequestMapping("/hello")
    public void hello(HttpServletRequest req, HttpServletResponse resp) throws IOException, IOException {
        resp.getWriter().write("Hello World from Custom Dispatcher!");
    }
}
