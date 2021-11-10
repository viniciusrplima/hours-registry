package com.pacheco.hoursregistry.controller;

import com.pacheco.hoursregistry.api.GithubApi;
import com.pacheco.hoursregistry.api.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class GitHubController {

    @Autowired
    private GithubApi api;

    @GetMapping("/issues")
    public List<Issue> listGithubIssues() {
        try {
            return api.getIssues();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
