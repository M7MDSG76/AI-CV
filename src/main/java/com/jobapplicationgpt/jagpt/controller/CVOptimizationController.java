package com.jobapplicationgpt.jagpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.jobapplicationgpt.jagpt.DTO.CV;
import com.jobapplicationgpt.jagpt.DTO.ChatGPTRequest;
import com.jobapplicationgpt.jagpt.DTO.ChatGPTResponse;
import com.jobapplicationgpt.jagpt.entity.CVOptimizationRequest;
import com.jobapplicationgpt.jagpt.entity.UploadCustomRulesModel;
import com.jobapplicationgpt.jagpt.service.GPT3Service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:4200/")
@Controller
@RequestMapping("/ai")
public class CVOptimizationController {

    @Autowired
    private GPT3Service gpt3Service;

    @Qualifier("openaiRestTemplate") // We used the @Qualifier annotation to inject a RestTemplate bean that we’ll
                                     // create in the next section

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.api.url}")
    private String apiUrl;

    @PostMapping("/cv")
    public ResponseEntity<String> OptimizeCV(@RequestBody CV cv) {

        try {

            ChatGPTRequest request = gpt3Service.generateChatRequest(cv);

            System.out.println("request:\n" +
                    "request messages: " + request.getMessages().get(0).getRole() + "\n"
                    + "request model: " + request.getModel() + "\n"
                    + "request n: " + request.getN() + "\n"
                    + "request temperature: " + request.getTemperature() + "\n");

            // call the API
            ChatGPTResponse response = restTemplate.postForObject(apiUrl, request, ChatGPTResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CV optimization failed");
            }

            return ResponseEntity.ok(response.getChoices().get(0).getMessage().getContent());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CV optimization error");
        }

    }

}
