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

Second commit:

Lets implement PostConstruct and PreDestroy

we create postConstruct annotation
valid for methods

we will be using inside our class
@PostConstruct

