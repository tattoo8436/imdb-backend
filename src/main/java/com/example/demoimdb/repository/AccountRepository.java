package com.example.demoimdb.repository;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);

    Account findByUsername(String username);
}
