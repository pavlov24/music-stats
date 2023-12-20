package pavlov24.ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @Override
    public String toString() {
        return name;
    }
}
