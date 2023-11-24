package africa.semicolon.acebook.repositories;

import africa.semicolon.acebook.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
