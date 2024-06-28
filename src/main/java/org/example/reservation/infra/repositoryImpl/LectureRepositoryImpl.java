package org.example.reservation.infra.repositoryImpl;

import org.example.reservation.domain.domain.Lecture;
import org.example.reservation.domain.repository.LectureRepository;
import org.example.reservation.infra.entity.LectureEntity;
import org.example.reservation.infra.jpaRepository.LectureJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LectureRepositoryImpl implements LectureRepository {
    private final LectureJpaRepository lectureJpaRepository;

    public LectureRepositoryImpl(LectureJpaRepository lectureJpaRepository) {
        this.lectureJpaRepository = lectureJpaRepository;
    }

}