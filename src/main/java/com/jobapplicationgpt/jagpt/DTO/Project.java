package com.jobapplicationgpt.jagpt.DTO;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Project {
    
    private String projectName;
    private String startDate;
    private String endDate;
    private ArrayList<String> projectHighlights;
    
}
