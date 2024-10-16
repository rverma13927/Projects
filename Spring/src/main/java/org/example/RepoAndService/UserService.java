package org.example.RepoAndService;

import org.example.annotation.Autowired;
import org.example.annotation.Component;
import org.example.annotation.PostConstruct;

// Annotation to mark a class as a Component
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    void helpMe() {
        System.out.println("After creation of UserService");
        userRepository.save();
    }

    public void performOperation() {
        userRepository.save();
    }
}
