package com.jobapplicationgpt.jagpt.DTO;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CV {
    //personal info
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    private ArrayList<Education> education;

    private ArrayList<Experience> experience;

    private ArrayList<Project> projects;

    private ArrayList<String> skills;

    private ArrayList<String> languages;

    private ArrayList<Certificate> certificates;

}
