package org.example.reservation.domain.repository;

import org.example.reservation.domain.domain.LectureHistory;


public interface LectureHistoryRepository {
    boolean existsByUserIdAndSpecialLectureId(Long userId, Long specialLectureId);
    LectureHistory readByUserIdAndSpecialLectureId(Long userId, Long specialLectureId);
    LectureHistory create(Long userId, Long specialLectureId, String title, String tutor);
}
