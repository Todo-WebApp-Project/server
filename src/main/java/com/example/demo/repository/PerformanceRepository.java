package com.example.demo.repository;

import com.example.demo.domain.Performance;
import com.example.demo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;


public interface PerformanceRepository extends JpaRepository<Performance,Long>
{
    @Query("SELECT MAX(p.perform_id) FROM Performance p")
    Optional<Long> findMaxPerformId();
}
