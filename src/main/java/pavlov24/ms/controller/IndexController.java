package pavlov24.ms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class IndexController {

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("links", Map.of(
                "Исполнители", "artists/",
                "Альбомы", "albums/",
                "Жанры", "genres/"
                ));
        return "index";
    }

}
