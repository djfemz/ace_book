package africa.semicolon.acebook.repositories;

import africa.semicolon.acebook.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
