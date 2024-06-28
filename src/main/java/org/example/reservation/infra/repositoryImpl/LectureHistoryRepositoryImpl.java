package org.example.reservation.infra.repositoryImpl;

import org.example.reservation.domain.domain.LectureHistory;
import org.example.reservation.domain.repository.LectureHistoryRepository;
import org.example.reservation.infra.entity.LectureHistoryEntity;
import org.example.reservation.infra.jpaRepository.LectureHistoryJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LectureHistoryRepositoryImpl implements LectureHistoryRepository {
    private final LectureHistoryJpaRepository lectureHistoryJpaRepository;

    public LectureHistoryRepositoryImpl(LectureHistoryJpaRepository lectureHistoryJpaRepository) {
        this.lectureHistoryJpaRepository = lectureHistoryJpaRepository;
    }

    @Override
    public boolean existsByUserIdAndSpecialLectureId(Long userId, Long specialLectureId) {
        return lectureHistoryJpaRepository.existsByUserIdAndSpecialLectureId(userId, specialLectureId);
    }

    @Override
    public LectureHistory readByUserIdAndSpecialLectureId(Long userId, Long specialLectureId) {
        return lectureHistoryJpaRepository.findByUserIdAndSpecialLectureId(userId, specialLectureId)
                .orElseThrow(() -> new IllegalArgumentException("특강 신청 내역이 없습니다."))
                .toLectureHistory();
    }

    @Override
    public LectureHistory create(Long userId, Long specialLectureId, String title, String tutor) {
        if (lectureHistoryJpaRepository.existsByUserIdAndSpecialLectureId(userId, specialLectureId)) {
            throw new RuntimeException("이미 신청된 강의입니다.");
        }
        return lectureHistoryJpaRepository
                .save(new LectureHistoryEntity(userId, specialLectureId, title, tutor))
                .toLectureHistory();
    }
}
