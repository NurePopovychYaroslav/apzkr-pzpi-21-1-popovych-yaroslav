package ua.clamor1s.eLock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.clamor1s.eLock.utils.annotations.Register;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/home")
    @Register
    public String homePage(Model model) {
        model.addAttribute("message", "some message");
        return "home";
    }

    @GetMapping
    @Register
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
