package pavlov24.ms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pavlov24.ms.entity.Genre;

@Data
public class GenreDTO {

    @NotBlank
    private String name;


    public Genre toEntity() {
        Genre genre = new Genre();
        genre.setName(name);
        return genre;
    }

}
