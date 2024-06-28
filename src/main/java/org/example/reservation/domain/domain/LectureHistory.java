package org.example.reservation.domain.domain;

import java.util.Optional;

public record LectureHistory(
        Long id,
        Long userId,
        Long specialLectureId,
        String lectureTitle,
        String lectureTutor

) {
    public static Optional<LectureHistory> empty() {
        return Optional.empty();
    }
}