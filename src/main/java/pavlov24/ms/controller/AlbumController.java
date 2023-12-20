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

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("artists/{artistId}/albums/")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @GetMapping("add")
    public String add(@PathVariable Long artistId, Model model) {
        log.info("add album request for artist with id = {}", artistId);
        model.addAttribute("artist", artistRepository.findById(artistId).get());
        return "album-add";
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
        if (!result.hasErrors()) { // проверка, имеются ли ошибки валидации
            // если ошибок нет, то сохраняем объект типа Artist в базу данных
            Optional<Artist> artist = artistRepository.findById(request.getArtistId());
            if (artist.isPresent()) {
                Album album = request.toEntity();
                album.setArtist(artist.get());
                albumRepository.save(album);
            }
            // redirect: - позволяет выполнить переадресацию к нужному url
            // здесь мы фактически перебрасываем пользователя обратно на страницу с таблицей
            // это нужно для того, чтобы в модель не попал объект ArtistDTO
            // иначе у нас в полях формы для добавления новых объектов появятся те значения,
            // которые до этого были введены
            return "redirect:/artists/" + request.getArtistId();
        } else { // если ошибки все же есть, логируем их
            log.info("has errors: {}", result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList()));
        }
        // здесь мы не просто переходим по /artists/,
        // но в модель еще будет добавлена информация о том, что ввел пользователь,
        // а также информация об ошибках валидации, которые мы подсветим пользователю
        return add(request.getArtistId(), model);
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
        if (!result.hasErrors()) { // проверка, имеются ли ошибки валидации
            Optional<Artist> artist = artistRepository.findById(request.getArtistId());
            if (artist.isPresent()) {
                Album album = request.toEntity();
                album.setArtist(artist.get());
                albumRepository.save(album);
            }
            return "redirect:/albums/";

        } else {
            log.info("has errors: {}", result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList()));
        }
        return "edit-album";
    }

    @PostMapping("remove")
    public String remove(@Positive Long id) {
        albumRepository.deleteById(id);
        return "redirect:/album/";
    }

}
