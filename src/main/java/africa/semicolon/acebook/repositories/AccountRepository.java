package africa.semicolon.acebook.repositories;

import africa.semicolon.acebook.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.accountDetails.email=:username")
    Optional<Account> findAccountByEmail(String username);
}
