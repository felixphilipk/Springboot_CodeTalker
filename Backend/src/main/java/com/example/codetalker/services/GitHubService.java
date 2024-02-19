package com.example.codetalker.services;
import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;


@Service
public class GitHubService {
    private final WebClient webClient;

    public GitHubService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com").build();
    }

    public Flux<String> listRepositories(String accessToken){
        return this.webClient.get()
               .uri("/user/repos") 
               .headers(headers -> headers.setBearerAuth(accessToken))
               .retrieve()
               .bodyToFlux(String.class);
    }


    public Mono<String>getPomContent(String accessToken,String owner,String repo){

            return this.webClient.get()
                .uri("/repos/{owner}/{repo}/contents/pom.xml",owner,repo)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .map(response->{

                  JsonObject json = JsonParser.parseString(response).getAsJsonObject();
                  String contentBase64 = json.get("content").getAsString();
                 return new String(Base64.getDecoder().decode(contentBase64));
                });
    }
}
