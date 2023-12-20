package pavlov24.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pavlov24.ms.entity.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
