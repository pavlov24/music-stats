package pavlov24.ms.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;


public class TrackDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "spotify:track:.*")
    private String spotifyId;
    @Positive
    private Integer diskNumber;
    @Positive
    private Integer trackNumber;
    @Positive
    private Integer duration;
    @Positive
    private Integer tempo;
    private Long albumId;

}
