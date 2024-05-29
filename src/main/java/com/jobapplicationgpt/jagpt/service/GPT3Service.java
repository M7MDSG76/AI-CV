package com.jobapplicationgpt.jagpt.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jobapplicationgpt.jagpt.DTO.CV;
import com.jobapplicationgpt.jagpt.DTO.ChatGPTRequest;
import com.jobapplicationgpt.jagpt.DTO.Message;

@Service
public class GPT3Service {

    @Value("${openai.model}")
    private String model;

    public String generateCV(CV cv) {
        /**
         * This method should prepare the CV object to be sent to the GPT3 API
         */

        ArrayList<String> sectionsWithErrors = new ArrayList<String>();
        String fullCV = "";

        fullCV += "First Name: " + cv.getFirstName() + " " + "Last Name: " + cv.getLastName() + "\n";
        fullCV += cv.getEmail() + " | " + cv.getPhone() + " | " + cv.getAddress() + "\n";

        try {
            fullCV += "Education \n";
            for (int i = 0; i < cv.getEducation().size(); i++) {
                fullCV += cv.getEducation().get(i).getOrgnizationName() + "         "
                        + cv.getEducation().get(i).getStartDate() + " - " + cv.getEducation().get(i).getEndDate()
                        + "\n";

                fullCV += cv.getEducation().get(i).getDegree() + "";
                fullCV += cv.getEducation().get(i).getMajor() + "\n";
                fullCV += "Degree: " + cv.getEducation().get(i).getGrade() + "\n";
            }
        } catch (Exception e) {
            sectionsWithErrors.add("Education");
            System.out.println(e.getMessage());
        }

        try {
            fullCV += "Experience \n";
            for (int i = 0; i < cv.getExperience().size(); i++) {
                fullCV += cv.getExperience().get(i).getCompanyName() + "         "
                        + cv.getExperience().get(i).getStartDate() + " - " + cv.getExperience().get(i).getEndDate()
                        + "\n";

                fullCV += cv.getExperience().get(i).getJobTitle() + "\n";

                for (int j = 0; j < cv.getExperience().get(i).getWorkHighlights().size(); j++) {

                    fullCV += "- " + cv.getExperience().get(i).getWorkHighlights().get(j) + "\n";
                }
            }
        } catch (Exception e) {
            sectionsWithErrors.add("Experience");
            System.out.println(e.getMessage());
        }

        try {
            fullCV += "Projects \n";
            for (int i = 0; i < cv.getProjects().size(); i++) {
                fullCV += cv.getProjects().get(i).getProjectName() + "         "
                        + cv.getProjects().get(i).getStartDate() + " - " + cv.getProjects().get(i).getEndDate() + "\n";

                for (int j = 0; j < cv.getProjects().get(i).getProjectHighlights().size(); j++) {
                    fullCV += "- " + cv.getProjects().get(i).getProjectHighlights().get(j) + "\n";
                }
            }
        } catch (Exception e) {
            sectionsWithErrors.add("Projects");
            System.out.println(e.getMessage());
        }

        try {
            fullCV += "Skills \n";
            for (int i = 0; i < cv.getSkills().size(); i++) {
                fullCV += cv.getSkills().get(i) + "\n";
            }
        } catch (Exception e) {
            sectionsWithErrors.add("Skills");
            System.out.println(e.getMessage());
        }

        try {
            fullCV += "Languages\n";
            for (int i = 0; i < cv.getLanguages().size(); i++) {
                fullCV += cv.getLanguages().get(i) + "\n";
            }
        } catch (Exception e) {
            sectionsWithErrors.add("Languages");
            System.out.println(e.getMessage());
        }

        try {
            fullCV += "Certifications \n";
            for (int i = 0; i < cv.getCertificates().size(); i++) {
                fullCV += cv.getCertificates().get(i).getCertificateName() + " | "
                        + cv.getCertificates().get(i).getOrganization() + "         "
                        + cv.getCertificates().get(i).getDate() + "\n";

            }
        } catch (Exception e) {
            sectionsWithErrors.add("Certifications");
            System.out.println(e.getMessage());
        }
        System.out.println(fullCV);
        return fullCV;

    }

    public ChatGPTRequest generateChatRequest(CV cv) {
        /**
         * This method should prepare the CVOptimizationRequest object to be sent to the
         * GPT3 API
         */

        ChatGPTRequest request = new ChatGPTRequest();
        request.setModel(model);
        request.setN(1);
        request.setTemperature(0);
        String instructions = """
                change the CV so the CV applies these rules, update the CV accordingly.
                - date formats should be in format "Nov 12th 2021"
                - The sections header should be in a capital case.
                - CV should include the first name, last name, email, phone number, LinkedIn, University name,
                  major, and start - end date of the major Study.
                - if the CV contains courses the name should be in this format: "Course name | Provider Name | date".
                - Check that the technical details are correct and phrased correctly.
                - if the position name is incorrect recommend the best name for the posiiton.
                - if any information is missing, ask the user to send the CV with the missing data.
                - if the CV contains a project, the project name should be in a capital case.
                - the response should be in this format:
                {
                   
                    [
                        {
                            "section": "section name",
                            "field": "field name",
                            "suggestion": "the suggestion",
                            "reason": "the reason"
                        },
                        ...etc
                        ]
                }
                if the suggestion is equal to the input keep the suggestion and reason fields empty.
                    """;

        Message systemMessage = new Message();
        systemMessage.setContent(instructions);
        systemMessage.setRole("system");

        Message userMessage = new Message();
        userMessage.setContent(generateCV(cv));
        userMessage.setRole("user");

        ArrayList<Message> messages = new ArrayList<Message>();
        messages.add(systemMessage);
        messages.add(userMessage);

        request.setMessages(messages);

        return request;
    }
}
