package africa.semicolon.acebook.repositories;

import africa.semicolon.acebook.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
