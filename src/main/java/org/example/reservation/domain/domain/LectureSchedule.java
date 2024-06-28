package org.example.reservation.domain.domain;

import java.time.LocalDateTime;

public record LectureSchedule (
        Long id,
        Long lectureId,
        Long capacity,
        LocalDateTime openTime,

        String lectureTitle,

        String lectureTutor
) {
    public LectureSchedule enroll() {
        if (capacity <= 0) {
            throw new RuntimeException("신청 마감");
        }
        return new LectureSchedule(id, lectureId, capacity - 1, openTime, lectureTitle, lectureTutor);
    }
}