package org.example.reservation.domain.repository;

import org.example.reservation.domain.domain.Lecture;
import org.example.reservation.domain.domain.LectureSchedule;

import java.util.List;

public interface LectureScheduleRepository {

    LectureSchedule readById(Long id);

    LectureSchedule applyLecture(LectureSchedule lectureSchedule);

    LectureSchedule addSpecialLecture(LectureSchedule lectureSchedule);

    List<LectureSchedule> readAll();


}
