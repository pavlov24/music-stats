package pavlov24.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pavlov24.ms.entity.TrackProperties;

public interface TrackPropertiesRepository extends JpaRepository<TrackProperties, Long> {
}
