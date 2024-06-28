package org.example.reservation.api.dto.response;

import org.example.reservation.domain.domain.LectureSchedule;

import java.util.List;

public record LecturesResponse(List<LectureResponse> lectures) {
    public static LecturesResponse from(List<LectureSchedule> lectures) {
        return new LecturesResponse(lectures.stream()
                .map(LectureResponse::from)
                .toList());
    }
}