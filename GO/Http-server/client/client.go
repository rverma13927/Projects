package main

import (
	"fmt"
	"net"
)

func main() {
	conn, err := net.Dial("tcp", "localhost:8080")
	if err != nil {
		fmt.Println("Failed to connect:", err)
		return
	}
	defer conn.Close()

	fmt.Println("Connected to server")

	// You can now use the conn variable to send and receive data
	// For example, you can write a message to the server:
	message := "Hello from fgdgdf"
	conn.Write([]byte(message + "\n"))

	// And read the response from the server:
	buf := make([]byte, 1024)
	n, err := conn.Read(buf)
	if err != nil {
		fmt.Println("Failed to read:", err)
		return
	}
	response := string(buf[:n])
	fmt.Println("Response from server:", response)
}