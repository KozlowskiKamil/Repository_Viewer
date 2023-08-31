package com.atipera.atipera.service;

import com.atipera.atipera.model.Repository;
import com.atipera.atipera.service.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubApiService {

    private final RestTemplate restTemplate;
    private final String githubApiUrl = "https://api.github.com";

    public GithubApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Repository> getPublicRepositories(String username) throws UserNotFoundException {
        try {
            String url = githubApiUrl + "/users/" + username + "/repos";
            ResponseEntity<Repository[]> response = restTemplate.getForEntity(url, Repository[].class);
            Repository[] repositories = response.getBody();

            if (repositories == null || repositories.length == 0) {
                throw new UserNotFoundException("User not found or has no public repositories");
            }

            return Arrays.asList(repositories);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not found");
        }
    }
}