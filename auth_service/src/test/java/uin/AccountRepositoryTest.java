package uin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository repository;

    @Test
    void shouldStoreNewAccount() {
        var newAccount = new AccountEntity("123456789", "joko", "joko@123", "joko@example.com");
        AccountEntity stored = repository.save(newAccount);

        assertThat(stored).isNotNull()
                .extracting("username", "credential", "email")
                .contains("joko", "joko@123", "joko@example.com");
    }

    @Test
    void shouldFindAccount() {
        repository.save(new AccountEntity("123456789", "joko", "joko@123", "joko@example.com"));
        Optional<AccountEntity> joko = repository.findByUsername("joko");
        assertThat(joko).isPresent();
    }
}