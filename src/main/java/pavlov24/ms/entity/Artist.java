package pavlov24.ms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String spotifyId;

    @OneToMany()
    @JoinColumn(name = "artist_id")
    @ToString.Exclude // аннотация исключает данное поле при формировании строкового представления
    private List<Album> albums;

    @ManyToMany
    private List<Genre> genres;

}
