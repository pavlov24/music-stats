package pavlov24.ms.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pavlov24.ms.dto.ArtistDTO;
import pavlov24.ms.dto.GenreDTO;
import pavlov24.ms.entity.Artist;
import pavlov24.ms.entity.Genre;
import pavlov24.ms.repository.GenreRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("genres/")
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping
    public String index(Model model, Integer pageNumber) {
        if (pageNumber == null) pageNumber = 0;
        // findAll() - возвращет все записи в таблице
        model.addAttribute("list",  genreRepository.findAll(PageRequest.of(pageNumber,10)));

        return "genres"; // имя шаблона
    }

    /**
     * Принимаем новую запись.
     * @param request - специальный тип объектов, где декларативно задана валидация полей
     * @param result - специальный объект, который позволяет просмотреть результаты валидации
     * @param model - данные (карты), которые будут доступны в шаблоне
     * @return название шаблона, на который нужно перейти
     */
    @PostMapping("add")
    public String add(@Valid GenreDTO request, BindingResult result, Model model) {
        log.info("add genre request {}, binding result = {}", request, result.hasErrors());
        if (!result.hasErrors()) { // проверка, имеются ли ошибки валидации
            // если ошибок нет, то сохраняем объект типа Artist в базу данных
            genreRepository.save(request.toEntity());
            // redirect: - позволяет выполнить переадресацию к нужному url
            // здесь мы фактически перебрасываем пользователя обратно на страницу с таблицей
            // это нужно для того, чтобы в модель не попал объект ArtistDTO
            // иначе у нас в полях формы для добавления новых объектов появятся те значения,
            // которые до этого были введены
            return "redirect:/genres/";
        } else { // если ошибки все же есть, логируем их
            log.info("has errors: {}", result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList()));
        }
        // здесь мы не просто переходим по /artists/,
        // но в модель еще будет добавлена информация о том, что ввел пользователь,
        // а также информация об ошибках валидации, которые мы подсветим пользователю
        return index(model, null);
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Genre> artist = genreRepository.findById(id);
        // для совместимости с кодом из шаблона на добавление объекта
        // будем использовать ключ artistDTO в модели
        artist.ifPresent(value -> model.addAttribute("genreDTO", value));

        return "edit-genre";
    }

    @PostMapping("edit")
    public String edit(@Valid GenreDTO request, BindingResult result) {
        log.info("edit genre request {}, binding result = {}", request, result.hasErrors());
        if (!result.hasErrors()) { // проверка, имеются ли ошибки валидации
            genreRepository.save(request.toEntity());
            return "redirect:/genres/";
        } else {
            log.info("has errors: {}", result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList()));
        }
        return "edit-genre";
    }

    @PostMapping("remove")
    public String remove(@Positive Long id) {
        genreRepository.deleteById(id);
        return "redirect:/genres/";
    }


}
