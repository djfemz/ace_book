package africa.semicolon.acebook.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Test
    @Sql(scripts = {"/db/data.sql"})
    void findAccountByEmailTest() {
        assertThat(accountRepository.findAccountByEmail("janet@email.com"))
                .isNotEmpty();
    }
}