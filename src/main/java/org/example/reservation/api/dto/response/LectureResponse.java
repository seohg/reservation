package org.example.reservation.api.dto.response;

import org.example.reservation.domain.domain.LectureSchedule;

import java.time.LocalDateTime;

public record LectureResponse(Long lectureId, String title, String tutor, Long capacity, LocalDateTime openTime) {
    public static LectureResponse from(LectureSchedule lectureSchedule) {
        return new LectureResponse(lectureSchedule.id(), lectureSchedule.lectureTitle(), lectureSchedule.lectureTutor(), lectureSchedule.capacity(), lectureSchedule.openTime());
    }
}
