package pavlov24.ms.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pavlov24.ms.dto.AlbumDTO;
import pavlov24.ms.entity.Album;
import pavlov24.ms.entity.Artist;
import pavlov24.ms.repository.AlbumRepository;
import pavlov24.ms.repository.ArtistRepository;
import pavlov24.ms.repository.TrackRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/tracks/")
@RequiredArgsConstructor
public class TrackController {


    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("list", trackRepository);
        model.addAttribute("albums", albumRepository.findAll());
        return "tracks"; // имя шаблона
    }

    /**
     * Принимаем новую запись.
     * @param request - специальный тип объектов, где декларативно задана валидация полей
     * @param result - специальный объект, который позволяет просмотреть результаты валидации
     * @param model - данные (карты), которые будут доступны в шаблоне
     * @return название шаблона, на который нужно перейти
     */
    @PostMapping("add")
    public String add(@Valid AlbumDTO request, BindingResult result, Model model) {
        log.info("add album request {}, binding result = {}", request, result.hasErrors());

        return index(model);
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Album> artist = albumRepository.findById(id);
        artist.ifPresent(value -> model.addAttribute("albumDTO", value));

        return "edit-album";
    }

    @PostMapping("edit")
    public String edit(@Valid AlbumDTO request, BindingResult result) {
        log.info("edit album request {}, binding result = {}", request, result.hasErrors());
        return "edit-album";
    }

    @PostMapping("remove")
    public String remove(@Positive Long id) {
        albumRepository.deleteById(id);
        return "redirect:/album/";
    }

}
