package africa.semicolon.acebook.repositories;

import africa.semicolon.acebook.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}
