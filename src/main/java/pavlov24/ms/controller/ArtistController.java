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
import pavlov24.ms.dto.ArtistDTO;
import pavlov24.ms.entity.Artist;
import pavlov24.ms.repository.ArtistRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j // позволяет выводить информацию в логи через log.info()
@Controller // помечаем класс как контроллер
@RequestMapping("/artists/") // название сущности во множественном числе
@RequiredArgsConstructor // генерация конструктора со всеми необходимыми final-полями
public class ArtistController {

    // ссылка интерфейсная, саму реализацию передает нам Spring
    private final ArtistRepository artistRepository;

    @GetMapping // без параметров, следовательно, реагируем на GET /artists/
    public String index(Model model) {
        // findAll() - возвращет все записи в таблице
        model.addAttribute("list", artistRepository.findAll());
        return "artists"; // имя шаблона
    }

    /**
     * Принимаем новую запись.
     * @param request - специальный тип объектов, где декларативно задана валидация полей
     * @param result - специальный объект, который позволяет просмотреть результаты валидации
     * @param model - данные (карты), которые будут доступны в шаблоне
     * @return название шаблона, на который нужно перейти
     */
    @PostMapping("add")
    public String add(@Valid ArtistDTO request, BindingResult result, Model model) {
        log.info("add artist request {}, binding result = {}", request, result.hasErrors());
        if (!result.hasErrors()) { // проверка, имеются ли ошибки валидации
            // если ошибок нет, то сохраняем объект типа Artist в базу данных
            artistRepository.save(request.toEntity());
            // redirect: - позволяет выполнить переадресацию к нужному url
            // здесь мы фактически перебрасываем пользователя обратно на страницу с таблицей
            // это нужно для того, чтобы в модель не попал объект ArtistDTO
            // иначе у нас в полях формы для добавления новых объектов появятся те значения,
            // которые до этого были введены
            return "redirect:/artists/";
        } else { // если ошибки все же есть, логируем их
            log.info("has errors: {}", result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList()));
        }
        // здесь мы не просто переходим по /artists/,
        // но в модель еще будет добавлена информация о том, что ввел пользователь,
        // а также информация об ошибках валидации, которые мы подсветим пользователю
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
        Optional<Artist> artist = artistRepository.findById(id);
        // для совместимости с кодом из шаблона на добавление объекта
        // будем использовать ключ artistDTO в модели
        artist.ifPresent(value -> model.addAttribute("artistDTO", value));

        return "edit-artist";
    }

    @PostMapping("edit")
    public String edit(@Valid ArtistDTO request, BindingResult result) {
        log.info("edit artist request {}, binding result = {}", request, result.hasErrors());
        if (!result.hasErrors()) { // проверка, имеются ли ошибки валидации
            artistRepository.save(request.toEntity());
            return "redirect:/artists/";
        } else {
            log.info("has errors: {}", result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList()));
        }
        return "edit-artist";
    }

    @PostMapping("remove")
    public String remove(@Positive Long id) {
        artistRepository.deleteById(id);
        return "redirect:/artists/";
    }

}
