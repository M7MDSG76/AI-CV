package com.jobapplicationgpt.jagpt.DTO;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Experience {

    private String companyName;
    private String jobTitle;
    private String location;
    private ArrayList<String> workHighlights;
    private String startDate;
    private String endDate;
}
