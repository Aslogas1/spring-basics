package main;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DefaultController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Главная страница");
        return "home";
    }
}
