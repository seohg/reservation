package org.example.reservation.infra.jpaRepository;

import org.example.reservation.infra.entity.LectureHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureHistoryJpaRepository extends JpaRepository<LectureHistoryEntity, Long> {
    boolean existsByUserIdAndSpecialLectureId(Long userId, Long specialLectureId);

    Optional<LectureHistoryEntity> findByUserIdAndSpecialLectureId(Long userId, Long specialLectureId);

}
