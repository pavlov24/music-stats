package pavlov24.ms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import pavlov24.ms.entity.Artist;

@Data
public class ArtistDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "spotify:artist:.*")
    private String spotifyId;


    public Artist toEntity() {
        Artist artist = new Artist();
        artist.setId(id);
        artist.setName(name);
        artist.setSpotifyId(spotifyId);
        return artist;
    }

}
