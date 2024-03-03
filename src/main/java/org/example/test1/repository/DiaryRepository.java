package org.example.test1.repository;

import org.example.test1.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserId(String userId);
    List<Diary> findByUserIdAndNoTag(String userId, Long noTag);
    void deleteByUserId(String userId);
}
