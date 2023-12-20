package pavlov24.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pavlov24.ms.entity.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
