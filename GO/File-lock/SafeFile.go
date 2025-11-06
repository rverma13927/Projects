package main

import (
	"fmt"
	"sync"
	"time"
)

type SafeFile struct {
	content string
	mutex   sync.RWMutex
}

func (f *SafeFile) read(g string) {
	f.mutex.RLock()
	fmt.Println(f.content + " " + g)
	time.Sleep(5 * time.Second)
	f.mutex.RUnlock()
}

func (f *SafeFile) write() {
	f.mutex.Lock()
	f.content = "hello1223"
	fmt.Println(f.content)
	f.mutex.Unlock()
}

func main() {
	f := SafeFile{"hello", sync.RWMutex{}}
	var wg sync.WaitGroup

	wg.Add(2)

	go func() {
		defer wg.Done()
		f.read("1")
	}()

	go func() {
		defer wg.Done()
		f.write()
	}()

	f.read("2") // runs in main goroutine

	wg.Wait()
}
