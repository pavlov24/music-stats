package pavlov24.ms.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String spotifyId;
    private String name;
    private LocalDate releaseDate;
    private String imgUrl;

    @ManyToOne
    private Artist artist;

    @OneToMany
    @JoinColumn(name = "album_id")
    private List<Track> tracks;

}
