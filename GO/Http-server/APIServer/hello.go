package main

import (
	"fmt"
	"net/http"
)
//https://medium.com/@emonemrulhasan35/net-http-package-in-go-e178c67d87f1
type GreetingHandler struct {
	Greeting string
}

func (h *GreetingHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	// Log the request method and path to the console (server side)
	fmt.Printf("Handling request: %s %s\n", r.Method, r.URL.Path)

	// Write the response back to the client using the ResponseWriter
	fmt.Fprintf(w, "%s from the custom handler!\n", h.Greeting)
}
func main() {

	helloHandler := &GreetingHandler{Greeting: "Hello, World"}

	http.HandleFunc("/hello", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprintf(w, "Building from scratch\n")
	})

	http.Handle("/foo", helloHandler)
	//http.ListenAndServe(":8080",nil);

	if err := http.ListenAndServe(":8080", nil); err != nil {
		fmt.Println("Failed to start server:", err)
	}

}
