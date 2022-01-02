package com.pacheco.hoursregistry.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Model {

    private Map<String, String> links;

    public void add(Link link) {
        if (links == null) {
            links = new HashMap<>();
        }

        System.out.println(links);
        links.put(link.getRel().value(), link.getHref());
    }

}
