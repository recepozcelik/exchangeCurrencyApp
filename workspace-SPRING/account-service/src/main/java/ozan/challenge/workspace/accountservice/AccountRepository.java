package ozan.challenge.workspace.accountservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select u from Account u where u.accountType = ?1")
    Optional<Account> findByAccountType(String accountType);

    @Query("select u from Account u where u.userName = ?1")
    List<Account> findByUser(String userName);
}

