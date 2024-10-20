WebClient in Spring is a non-blocking, reactive client used to perform HTTP requests. It is part of the Spring WebFlux module and is designed to replace the legacy RestTemplate, offering better support for modern asynchronous programming with non-blocking I/O.

Here’s a detailed explanation of how WebClient works and its components:
Key Features of WebClient

    Non-blocking I/O: WebClient performs requests and processes responses asynchronously, making it suitable for applications where you need high scalability and performance.
    Reactive: It integrates seamlessly with Project Reactor to provide support for reactive streams (Mono, Flux).
    Supports all HTTP methods: GET, POST, PUT, DELETE, PATCH, etc.
    Easy-to-use API: You can build requests and handle responses using a fluent and intuitive API.

Basic Components of WebClient

    Builder: Used to configure and create an instance of WebClient.
    Request: You can define an HTTP request with headers, query parameters, and body content.
    Response Handling: Handle the response in various formats, including string, JSON, and custom objects, using Mono or Flux



Mono:



In Spring WebFlux and Project Reactor, a Mono represents a reactive stream that emits at most one item (a single value or an empty signal) and then completes.

Mono is part of the reactive programming paradigm, where it is used to handle asynchronous computations. It’s analogous to a Future or a CompletableFuture in Java but with more powerful capabilities for chaining operations and handling asynchronous tasks reactively.
Key Characteristics of Mono:

    Zero or One Value: A Mono can either emit one value or no value at all (i.e., it can complete empty).
    Asynchronous: Like all reactive types, Mono is non-blocking and works asynchronously.
    Event-driven: It emits values when they become available, handling the events of success, error, or completion.

Typical Use Cases

    Return a single object or resource (e.g., a single HTTP response).
    Perform an asynchronous task that either succeeds or fails.
    Make a request to a remote service where you expect one response (e.g., calling a REST API and receiving a single result).

Example of a Mono

java

import reactor.core.publisher.Mono;

public class MonoExample {

    public static void main(String[] args) {
        // Create a Mono that emits a single value
        Mono<String> mono = Mono.just("Hello, Reactive World!");

        // Subscribe to the Mono to consume the value
        mono.subscribe(value -> System.out.println(value));
    }
}

How Mono Works:

    In this example, Mono.just("Hello, Reactive World!") creates a Mono that emits the value "Hello, Reactive World!".
    When you subscribe to the Mono, it triggers the emission of the value, and you handle that value in the lambda function (value -> System.out.println(value)).

Chaining Operations with Mono

You can chain operations on a Mono using methods like map, flatMap, and filter, similar to how you work with Java streams.

Example:

java

Mono<String> mono = Mono.just("hello")
.map(String::toUpperCase) // Transform the string to uppercase
.filter(value -> value.length() > 3); // Apply a filter

Handling Empty and Error Signals

    If a Mono doesn't emit a value, it completes as empty. This can be handled with Mono.empty().
    Errors can be handled using onErrorResume, onErrorReturn, or similar methods.

Example with error handling:

java

Mono<String> mono = Mono.just("Hello")
.map(value -> {
if (value.equals("Hello")) {
throw new RuntimeException("Error occurred!");
}
return value;
})
.onErrorReturn("Default Value");

mono.subscribe(System.out::println); // Output: Default Value

Common Mono Methods:

    Mono.just(T value): Creates a Mono that emits the given value.
    Mono.empty(): Creates a Mono that completes without emitting any value.
    Mono.error(Throwable error): Creates a Mono that terminates with the given error.
    map(): Transforms the emitted item.
    flatMap(): Asynchronously transforms the emitted item, returning another Mono.
    onErrorResume(): Provides an alternative Mono in case of an error.
    onErrorReturn(): Returns a default value if an error occurs.

Mono in WebClient

When using WebClient in Spring WebFlux, HTTP requests return a Mono of the response, because these operations are asynchronous.

Example of using Mono with WebClient:

java

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientExample {

    private WebClient webClient = WebClient.create();

    public Mono<String> fetchResource() {
        return webClient.get()
                        .uri("http://example.com/resource")
                        .retrieve()
                        .bodyToMono(String.class);  // Returns a Mono of the response body
    }
}

In this case, the fetchResource() method returns a Mono<String>, which represents the asynchronous result of the HTTP GET request.
Conclusion

Mono is a key part of reactive programming with Project Reactor, particularly in Spring WebFlux. 
It handles asynchronous computations and can emit either a single value or no value (empty), 
making it ideal for tasks like web requests where a single response is expected.
