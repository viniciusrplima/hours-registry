package com.pacheco.hoursregistry.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class GithubApi {

    @Value("${github.auth.token}")
    private String token;

    private final String BASE_URL="https://api.github.com";

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.defaultHeader("Authorization", "token " + token).build();
    }

    public List<Issue> getIssues() throws Exception {
        Issue[] issues = restTemplate.getForObject(BASE_URL + "/issues", Issue[].class);
        return Arrays.asList(issues);
    }

}
