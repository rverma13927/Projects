package com.example.rateLimiting.entity;

import com.example.rateLimiting.enums.Plans;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;
    private Plans plans;

}
