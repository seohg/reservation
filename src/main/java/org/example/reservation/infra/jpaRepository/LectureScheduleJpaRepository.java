package org.example.reservation.infra.jpaRepository;

import jakarta.persistence.LockModeType;
import org.example.reservation.infra.entity.LectureScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface LectureScheduleJpaRepository extends JpaRepository<LectureScheduleEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureScheduleEntity> findById(Long id);

    boolean existsById(Long id);

}
