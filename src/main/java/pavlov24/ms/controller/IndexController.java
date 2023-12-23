package pavlov24.ms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pavlov24.ms.entity.User;
import pavlov24.ms.repository.UserRepository;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("links", Map.of(
                "Исполнители", "artists/",
                "Альбомы", "albums/",
                "Жанры", "genres/"
                ));
        return "index";
    }


// Добавить пользователей
//    @GetMapping("/add/")
//    public String add() {
//        var user = new User();
//        user.setUsername("user");
//        user.setPassword(passwordEncoder.encode("user"));
//        user.setEnabled(true);
//        user.setAuthorities(List.of(() -> "USER"));
//        user.setAccountNonLocked(true);
//        user.setCredentialsNonExpired(true);
//        user.setAccountNonExpired(true);
//
//        userRepository.save(user);
//        return "index";
//    }

}
