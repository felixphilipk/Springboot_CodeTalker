package com.example.codetalker.controller;

import com.example.codetalker.model.*;
import com.example.codetalker.services.*;

import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")

public class RepositoryController {

    private final GitHubService gitHubService;
    private final PomParserService pomParserService;

    @Autowired
    public RepositoryController(GitHubService gitHubService, PomParserService pomParserService){
        this.gitHubService = gitHubService;
        this.pomParserService =pomParserService;
    }

    @GetMapping("/dependencies")
    public Mono<PomProject> getDependencies(@RequestParam String accessToken, @RequestParam String repoName,@RequestParam String owner) throws JAXBException {
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
    

    
}
