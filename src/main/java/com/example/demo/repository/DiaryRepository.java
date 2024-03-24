package com.example.demo.repository;

import com.example.demo.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserId(String userId);
    List<Diary> findByUserIdAndNoTag(String userId, Long noTag);
    void deleteAllByUserId(String userId);
}
