package org.example;

import org.example.annotation.Autowired;
import org.example.annotation.Component;

// Annotation to mark a class as a Component
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void performOperation() {
        userRepository.save();
    }
}
