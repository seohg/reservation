package org.example.reservation.api.dto.response;

import org.example.reservation.domain.domain.LectureHistory;

public record ApplyResponse(
        Long reservationId,
        Long userId,
        Long specialLectureId,
        String lectureTitle,
        String lectureTutor) {
    public static ApplyResponse from(LectureHistory lectureHistory) {
        return new ApplyResponse(
                lectureHistory.id(),
                lectureHistory.userId(),
                lectureHistory.specialLectureId(),
                lectureHistory.lectureTitle(),
                lectureHistory.lectureTutor()
        );
    }
}