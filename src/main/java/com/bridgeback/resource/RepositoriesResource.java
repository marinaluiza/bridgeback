package com.bridgeback.resource;


import com.bridgeback.model.Repository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class RepositoriesResource {

    @RequestMapping(value = "/repositories/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Repository>> listRepositories(@PathVariable String name) {
        final String uri = "https://api.github.com/search/repositories?q=" + name;
        return getResponse(uri);
    }

    @RequestMapping(value = "/users/{owner}/repos", method = RequestMethod.GET)
    public ResponseEntity<List<Repository>> getUserRepository(@PathVariable String owner) {
        final String uri = "https://api.github.com/users/" + owner + "/repos";
        return getResponse(uri);
    }

    private ResponseEntity<List<Repository>> getResponse(String uri) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            List<Repository> repositories = Arrays.asList(mapper.readValue(root.toString(), Repository[].class));
            return ResponseEntity.status(HttpStatus.OK).body(repositories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
