package com.jobapplicationgpt.jagpt.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatGPTRequest {
    
    private String model;
    private ArrayList<Message> messages;
    private int n; //Number of responses to generate
    private double temperature; //Randomness of the response

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;

        Message message = new Message();
        
        this.messages = new ArrayList<Message>();
        this.messages.add(message);
    }

}
