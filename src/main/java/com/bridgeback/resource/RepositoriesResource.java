package com.bridgeback.resource;


import com.bridgeback.model.Repository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class RepositoriesResource {

    @CrossOrigin
    @RequestMapping(value = "/repositories/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Repository>> listRepositories(@PathVariable String name) {
        try {
            ResponseEntity<String> response = getResponse("https://api.github.com/search/repositories?q=" + name);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody()).path("items");
            List<Repository> repositories = Arrays.asList(mapper.readValue(root.toString(), Repository[].class));
            return ResponseEntity.status(HttpStatus.OK).body(repositories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/users/{owner}/repos", method = RequestMethod.GET)
    public ResponseEntity<List<Repository>> getUserRepository(@PathVariable String owner) {
        try {
            ResponseEntity<String> response = getResponse("https://api.github.com/users/" + owner + "/repos");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            List<Repository> repositories = Arrays.asList(mapper.readValue(root.toString(), Repository[].class));
            return ResponseEntity.status(HttpStatus.OK).body(repositories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    private ResponseEntity<String> getResponse(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(uri, String.class);
    }
}
