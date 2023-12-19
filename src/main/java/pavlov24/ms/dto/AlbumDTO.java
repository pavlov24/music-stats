package pavlov24.ms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pavlov24.ms.entity.Album;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class AlbumDTO {

    private Long id;
    @NotBlank
    @Pattern(regexp = "spotify:album:.*")
    private String spotifyId;

    @NotBlank
    private String name;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate releaseDate;

    private String imgUrl;

    @NotNull
    private Long artistId;


    public Album toEntity() {
        Album album = new Album();
        album.setId(id);
        album.setName(name);
        album.setSpotifyId(spotifyId);
        album.setReleaseDate(releaseDate);
        album.setImgUrl(imgUrl);
        return album;
    }
}
