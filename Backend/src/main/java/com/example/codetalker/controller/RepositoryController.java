package com.example.codetalker.controller;

import com.example.codetalker.model.*;
import com.example.codetalker.services.*;

import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;



@RestController
@RequestMapping("/api")

public class RepositoryController {



    private final GitHubService gitHubService;
    private final PomParserService pomParserService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public RepositoryController(GitHubService gitHubService, PomParserService pomParserService,OAuth2AuthorizedClientService authorizedClientService){
        this.gitHubService = gitHubService;
        this.pomParserService =pomParserService;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/dependencies")
    public Mono<PomProject> getDependencies(@RequestParam String repoName,@RequestParam String owner, Authentication authentication) throws JAXBException {
        String userName = authentication.getName();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService
        .loadAuthorizedClient("github", userName);
        if(authorizedClient!=null & authorizedClient.getAccessToken()!= null){
            String accessToken = authorizedClient.getAccessToken().getTokenValue();
        return gitHubService.getPomContent(accessToken, owner, repoName)
        .flatMap(pomContent->{
            try{
               PomProject pomProject = pomParserService.parsePOMXml(pomContent);
                return Mono.just(pomProject);
            }
            catch(JAXBException e){
                return Mono.error(e);

            }
        });
    }
    else{
        return Mono.error(new IllegalStateException("No access token available"));
    }
}


    
}
