package pavlov24.ms.entity;

import jakarta.persistence.*;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String spotifyId;
    private Integer diskNumber;
    private Integer trackNumber;
    private Integer duration;
    private Integer tempo;

    @ManyToOne
    private Album album;

}
