package org.example.test1.Jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface BlacklistRepository extends JpaRepository<Blacklist, String> {
    void deleteByDeleteAtBefore(Date deleteAt);
}
