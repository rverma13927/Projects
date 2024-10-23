package com.example.rateLimiting.services;

import com.example.rateLimiting.entity.User;
import com.example.rateLimiting.enums.Plans;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private List<User> user = new ArrayList<>();
    UserServiceImpl(){
        user.add(new User("Abc", Plans.BASIC));
        user.add(new User("XYZ", Plans.PREMIUM));
        user.add(new User("MNP", Plans.FREE));
    }
    @Override
    public List<User> getUsers() {
        return user;
    }
}
