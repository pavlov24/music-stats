package pavlov24.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pavlov24.ms.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
