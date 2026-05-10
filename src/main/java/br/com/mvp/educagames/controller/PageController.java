package br.com.mvp.educagames.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "forward:/register.html";
    }

    @GetMapping("/games")
    public String games() {
        return "forward:/games.html";
    }

    @GetMapping("/games/{slug}")
    public String game(@PathVariable String slug) {
        return "forward:/game.html";
    }

    @GetMapping("/profile")
    public String profile() {
        return "forward:/profile.html";
    }

    @GetMapping("/ranking")
    public String ranking() {
        return "forward:/ranking.html";
    }
}
