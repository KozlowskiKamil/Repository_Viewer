package com.atipera.atipera.controller;

import com.atipera.atipera.model.ErrorResponse;
import com.atipera.atipera.service.GithubApiService;
import com.atipera.atipera.model.Repository;
import com.atipera.atipera.service.UserNotFoundException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GithubApiController {

    private final GithubApiService githubApiService;

    public GithubApiController(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }


    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getGithubRepositories(
            @PathVariable String username,
            @RequestHeader("Accept") String acceptHeader
    ) {
        try {
            List<Repository> repositories = githubApiService.getPublicRepositories(username);

            if (acceptHeader != null) {
                if (acceptHeader.equals("application/xml")) {
                    String xmlResponse = convertRepositoriesToXml(repositories);
                    return ResponseEntity.ok(xmlResponse);
                } else if (acceptHeader.equals("application/json")) {
                    return ResponseEntity.ok(repositories);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Unsupported Media Type"));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String convertRepositoriesToXml(List<Repository> repositories) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(repositories);
    }
}