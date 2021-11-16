package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.model.GithubIssue;
import com.pacheco.hoursregistry.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.pacheco.hoursregistry.util.AuthorizationUtil.currentUsername;


@Slf4j
@Service
public class GithubApi {

    @Value("${github.auth.token}")
    private String token;

    private final String BASE_URL="https://api.github.com";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public List<GithubIssue> getIssues() throws Exception {
        User user = userService.find(currentUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "token " + user.getGithubToken());

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<GithubIssue[]> issues = restTemplate.exchange(BASE_URL + "/issues", HttpMethod.GET, entity, GithubIssue[].class);
        return Arrays.asList(issues.getBody());
    }

}
