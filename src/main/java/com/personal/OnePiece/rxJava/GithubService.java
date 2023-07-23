package com.personal.OnePiece.rxJava;

import com.personal.OnePiece.rxJava.model.GithubUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class GithubService {
    private static final String USER_API_BASE_URL = "https://api.github.com";
    private final WebClient webClient = WebClient.create(USER_API_BASE_URL);

    public Mono<GithubUser> getUserByUsername(String userName) {
        return webClient.get()
                .uri("/users/{userName}", userName)
                .retrieve()
                .bodyToMono(GithubUser.class);
    }
}
