package org.example;

import org.example.IOC.ApplicationContext;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        ApplicationContext context = new ApplicationContext();
        context.register(UserService.class);
        context.register(UserRepository.class);
        context.injectDependencies();

        UserService userService = context.getBean(UserService.class);
        userService.performOperation();
    }
}



