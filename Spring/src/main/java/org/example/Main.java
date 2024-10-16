package org.example;

import org.example.IOC.ApplicationContext;
import org.example.RepoAndService.UserRepository;
import org.example.RepoAndService.UserService;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        ApplicationContext context = new ApplicationContext();
        context.register(UserService.class);
        context.register(UserRepository.class);
        context.injectDependencies();

        // Add shutdown hook to call context.destroy() on JVM shutdown

        /*
         *
         * Runtime.getRuntime().addShutdownHook() is a method in Java that allows you to register a piece of code (usually a thread) that will be
         * executed when the Java Virtual Machine (JVM) shuts down. This is particularly useful for ensuring that resources are properly cleaned up,
         * for example, calling @PreDestroy methods, closing file streams, or releasing database connections, before the application terminates.
         * Key Points:

                Registering the Shutdown Hook:
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // Cleanup code
                context.destroy();
            }));

            This ensures that the context.destroy() method will be invoked when the JVM is shutting down.

            Triggering JVM Shutdown: The shutdown hook will be called automatically when the JVM is shutting down due to:

                Normal termination (e.g., after the main() method ends).
                When you call System.exit().
                When the JVM receives termination signals (e.g., CTRL+C or kill -15).

            Manual Shutdown: If you need to trigger a manual shutdown, you can use System.exit(0) to exit the program. This will trigger the shutdown hook.

            Thread in Shutdown Hook: The hook runs as a separate thread, so it doesn't block the main thread. You can include any cleanup logic inside the Runnable you pass to Thread.
            The shutdown hook won't run for abnormal terminations like killing the process (kill -9 on Unix systems) or power failure, but it will for normal exits or signals (e.g., CTRL+C or kill -15).
        */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Shutdown hook triggered, cleaning up resources...");
                context.destroy(); // Clean up resources and call @PreDestroy
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        UserService userService = context.getBean(UserService.class);
        userService.performOperation();
    }
}



