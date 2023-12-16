package pavlov24.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pavlov24.ms.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
