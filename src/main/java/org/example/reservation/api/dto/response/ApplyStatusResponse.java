package org.example.reservation.api.dto.response;

import org.example.reservation.domain.domain.LectureHistory;

import java.util.Optional;

public record ApplyStatusResponse(String status) {
    public static ApplyStatusResponse from(Optional<LectureHistory> lectureHistory) {
        if (lectureHistory.isEmpty()) {
            return new ApplyStatusResponse("특강 예약 내역이 없습니다.");
        }
        return new ApplyStatusResponse(lectureHistory.get().lectureTitle() + " 특강 예약 완료");
    }
}
