package com.pacheco.hoursregistry.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonRootName("issue")
public class Issue {

    private String id;

    private String title;

    private String url;

    @JsonProperty("repository_url")
    private String repositoryUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String number;

    private String state;

    private Boolean locked;

    private String body;

}
