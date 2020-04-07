package com.example.demo.services;

import com.example.demo.model.Cases;
import com.example.demo.model.Country;
import com.example.demo.model.JSONObject;
import com.example.demo.model.Mail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class Scheduler {

    @Autowired
    private IDao daoService;

    private Cases cases=Cases.getInstence();

    @Scheduled(fixedRate = 100000)
    public String sendMail() throws JsonProcessingException {
            final String uri = "https://coronavirus-monitor.p.rapidapi.com/coronavirus/cases_by_country.php";

        final HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host","coronavirus-monitor.p.rapidapi.com");
        headers.set("x-rapidapi-key", "6356e62fc7msh27272757a761a13p18e27cjsn8d58dbbf0f26");

        // HttpEntity<String>: To get result as String.
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.GET, entity, String.class);

        String result = response.getBody();

        ObjectMapper objectMapper=new ObjectMapper();
        JSONObject jsonObject=objectMapper.readValue(result,JSONObject.class);
        System.out.println(jsonObject.countries_stat.length);
        System.out.println(result);

        StringBuilder message=new StringBuilder();
        message.append("Updates : ( new informations ) !!\n\n");

        boolean sendMessage=false;

        for (Country country : jsonObject.countries_stat) {
            if(cases.add(country.country_name,country.cases,country.deaths,country.total_recovered)) {
                message.append(country.country_name + " ====> Cases : " + country.cases +
                ",   Deaths : " + country.deaths + ",   Total Recovered : " + country.total_recovered +
                ",   (New cases : " + country.new_cases + ",   New Deaths : " + country.new_deaths + 
                ",   Serious/critical : " + country.serious_critical + ",    Active Cases : " + country.active_cases + ") \n\n");
                sendMessage = true;
            }
        }

        if(sendMessage){
            message.append("\n LastUpdates : "+jsonObject.statistic_taken_at);

            Mail email=new Mail(null,message.toString(),"Covid Updates !! ");
            System.out.println("Exec............................");
            return daoService.sendEmail(email.getText(),email.getSubject());
        }

        return "No updates";
    }
}
