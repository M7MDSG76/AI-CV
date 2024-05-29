package com.jobapplicationgpt.jagpt.DTO;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatGPTResponse {
    private ArrayList<Choice> choices;

  
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Choice {

        private int index;
        private Message message;

       
    }
}
