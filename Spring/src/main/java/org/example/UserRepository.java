package org.example;

import org.example.annotation.Component;

@Component
public class UserRepository {
    public void save() {
        System.out.println("Saving user...");
    }
}
