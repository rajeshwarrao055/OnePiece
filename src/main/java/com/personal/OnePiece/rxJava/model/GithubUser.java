package com.personal.OnePiece.rxJava.model;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private String login;
    private Integer public_repos;
}
