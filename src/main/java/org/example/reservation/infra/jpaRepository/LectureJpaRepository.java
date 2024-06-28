package org.example.reservation.infra.jpaRepository;

import org.example.reservation.infra.entity.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {
    boolean existsByTitle(String title);
}

