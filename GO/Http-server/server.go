package main

import (
	"bufio"
	"fmt"
	"io"
	"log"
	"net"
	"strings"
)

func main() {
	listner, err := net.Listen("tcp", ":8080")
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println("Listening on port 8080")
	for {
		conn, err := listner.Accept()
		if err != nil {
			log.Println("Error accepting connection:", err)
			continue
		}
		go handleConnection(conn)
	}
}

func handleConnection(conn net.Conn) {
	defer conn.Close()

	reader := bufio.NewReader(conn)
	for {
		message, err := reader.ReadString('\n')
		if err != nil {
			if err != io.EOF && err.Error() != net.ErrClosed.Error() {
				log.Println("Error reading message:", err)
			}
			return
		}
		fmt.Print("Message Received:", string(message))
		newmessage := strings.ToUpper(message)
		_, err = conn.Write([]byte(newmessage + "\n"))
		if err != nil {
			log.Println("Error writing response:", err)
			return
		}
	}
}
