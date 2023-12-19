package pavlov24.ms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String spotifyId;

    @OneToMany
    @JoinColumn(name = "artist_id")
    private List<Album> albums;

}
