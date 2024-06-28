package org.example.reservation.infra.repositoryImpl;

import org.example.reservation.domain.domain.LectureSchedule;
import org.example.reservation.domain.repository.LectureScheduleRepository;
import org.example.reservation.infra.entity.LectureScheduleEntity;
import org.example.reservation.infra.jpaRepository.LectureScheduleJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LectureScheduleRepositoryImpl implements LectureScheduleRepository {

    private final LectureScheduleJpaRepository lectureScheduleJpaRepository;
    private final LectureScheduleJpaRepository lectureJpaRepository;
    public LectureScheduleRepositoryImpl(LectureScheduleJpaRepository lectureScheduleJpaRepository, LectureScheduleJpaRepository lectureJpaRepository) {
        this.lectureScheduleJpaRepository = lectureScheduleJpaRepository;
        this.lectureJpaRepository = lectureJpaRepository;
    }

    @Override
    public LectureSchedule readById(Long id) {
        return lectureScheduleJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("미존재 특강"))
                .toLectureSchedule();
    }

    @Override
    public LectureSchedule applyLecture(LectureSchedule lectureSchedule) {
        LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.findById(lectureSchedule.id())
                .orElseThrow(() -> new RuntimeException("미존재 강의"));

        if (LocalDateTime.now().isBefore(lectureSchedule.openTime())) {
            throw new RuntimeException("특강 오픈 전");
        }

        lectureScheduleEntity.updateCapacity(lectureSchedule);
        return lectureScheduleJpaRepository.save(lectureScheduleEntity).toLectureSchedule();
    }

    @Override
    public LectureSchedule addSpecialLecture(LectureSchedule lectureSchedule) {
        return lectureScheduleJpaRepository
                .save(new LectureScheduleEntity(lectureSchedule.lectureId(), lectureSchedule.capacity(), lectureSchedule.openTime(), lectureSchedule.lectureTitle(), lectureSchedule.lectureTutor()))
                .toLectureSchedule();
    }

    @Override
    public List<LectureSchedule> readAll() {
        return lectureScheduleJpaRepository.findAll().stream()
                .map(LectureScheduleEntity::toLectureSchedule)
                .toList();
    }


}
