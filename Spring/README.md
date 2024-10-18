1. Understand the Basics of Dependency Injection (DI) and Inversion of Control (IoC)

   Dependency Injection (DI): Objects should not instantiate their dependencies directly. Instead, they should be
   injected by a container.
   Inversion of Control (IoC): The control over the flow of a program is inverted; instead of objects controlling their
   own dependencies, a container takes control of them.

2. Design a Simple IoC Container

The IoC container will be the core of your framework. It will manage the lifecycle and injection of dependencies.

Here's how you could design a simple IoC container:

    Define components (@Component) or services that the container should manage.
    Register these components in a central ApplicationContext (container).
    Inject dependencies into these components when needed.

3. Support for Component Scanning
4. Implement Dependency Injection : Spring: First commit
5. Lifecycle Management : PostConstruct and PreDestroy
7. AOP (Aspect-Oriented Programming)
8. Transaction Management

First commit:
How can we create our own annotation in java and use them with the help of reflection
We created two annotation and applicationContext to store all the object of the classes annotated with those annotation.

7. AOP (Aspect-Oriented Programming)

Implement an aspect mechanism that allows you to apply cross-cutting concerns like logging, transactions, etc.

    You can implement this using proxy objects. A simple way is to create dynamic proxies using Java's Proxy class or using a third-party library like CGLib for class-based proxying.

8. Transaction Management

You can introduce a @Transactional annotation and wrap method calls in transactional logic, rolling back or committing
transactions based on exceptions.

Example:

    Define a @Transactional annotation and apply transaction logic before and after method execution using proxies.

9. Spring-Like Features You Can Add Gradually

Once you have your basic DI and IoC container working, you can start building more Spring-like features, such as:

    Aspect-Oriented Programming (AOP) for cross-cutting concerns.
    Data Access Layer: Implement your version of Spring Data JPA.
    MVC Framework: Build a lightweight MVC framework to handle HTTP requests and map them to controller methods.
    Security Framework: Develop security features similar to Spring Security for authentication and authorization.

Logging:

Hierarchy of Log Levels (From Least to Most Verbose):

    ERROR: Logs only critical failures.
    WARN: Logs warnings and errors.
    INFO: Logs informational messages, warnings, and errors.
    DEBUG: Logs debugging information, including informational, warnings, and errors.
    TRACE: Logs everything, including trace, debug, info, warnings, and errors.

