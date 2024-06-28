package org.example.reservation.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reservation.api.dto.request.ApplyRequest;
import org.example.reservation.api.dto.response.ApplyResponse;
import org.example.reservation.api.dto.response.LecturesResponse;
import org.example.reservation.api.dto.response.ApplyStatusResponse;
import org.example.reservation.domain.domain.LectureHistory;
import org.example.reservation.domain.domain.LectureSchedule;
import org.example.reservation.domain.service.LectureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    // 특강 목록 조회
    @GetMapping
    public LecturesResponse getLectures() {
        List<LectureSchedule> lectures = lectureService.getLectures();
        return LecturesResponse.from(lectures);
    }

    // 특강 신청
    @PostMapping("apply/{userId}")
    public ApplyResponse apply(@PathVariable Long userId, @RequestBody ApplyRequest request) {
        LectureHistory history = lectureService.reserve(userId, request);
        return ApplyResponse.from(history);
    }

    // 특강 목록 조회
    @GetMapping("application/{userId}")
    public ApplyStatusResponse getApplyStatus(@PathVariable Long userId, @RequestParam(value = "specialLectureId") Long specialLectureId) {
        Optional<LectureHistory> lectureHistory = lectureService.getApplyStatus(userId, specialLectureId);
        return ApplyStatusResponse.from(lectureHistory);
    }
}
