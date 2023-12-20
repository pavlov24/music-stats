package pavlov24.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pavlov24.ms.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
